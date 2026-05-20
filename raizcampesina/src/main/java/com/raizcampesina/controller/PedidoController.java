
package com.raizcampesina.controller;

import com.raizcampesina.model.Usuario;
import com.raizcampesina.model.Pedido;
import com.raizcampesina.model.Producto;
import com.raizcampesina.model.Favorito;
import com.raizcampesina.service.PedidoService;
import com.raizcampesina.service.ProductoService;
import com.raizcampesina.service.UsuarioService;
import com.raizcampesina.service.FavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import jakarta.servlet.http.HttpSession;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Controller
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProductoService productoService;
    
    @Autowired
    private FavoritoService favoritoService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/pedidos")
    public String mostrarPedidos(Model model, HttpSession session) {
        model.addAttribute("pedido", new com.raizcampesina.model.Pedido()); 

        List<Producto> productos = productoService.listarProductos();
        List<Favorito> favoritos = favoritoService.listarFavoritos(); // Asegura que el nombre coincida en tu servicio
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (productos != null && favoritos != null && usuarioLogueado != null) {
            for (Producto prod : productos) {
                boolean esFav = favoritos.stream().anyMatch(f -> 
                    f.getProducto() != null && f.getProducto().getIdProducto().equals(prod.getIdProducto()) &&
                    f.getUsuario() != null && f.getUsuario().getIdUsuario().equals(usuarioLogueado.getIdUsuario())
                );
                prod.setFavorito(esFav); 
            }
        }

        List<Usuario> todosLosUsuarios = usuarioService.listarUsuarios();
        List<Usuario> soloTransportadores = java.util.Collections.emptyList();

        if (todosLosUsuarios != null) {
            soloTransportadores = todosLosUsuarios.stream()
                .filter(u -> u.getRol() != null && 
                             "TRANSPORTADOR".equalsIgnoreCase(u.getRol().getNombreRol()))
                .toList();
        }

        model.addAttribute("listaProductos", productos);
        model.addAttribute("listaTransportadores", soloTransportadores);

        return "pedidos";
    }

    @PostMapping("/guardarPedido")
    public String guardarPedido(@ModelAttribute Pedido pedido, 
                                HttpSession session, 
                                RedirectAttributes redirectAttributes) {
        
        Usuario clienteLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        pedido.setUsuario(clienteLogueado);

        // 💡 ASEGURAR RELACIÓN: Buscamos el producto completo desde la BD para que 
        // traiga al objeto Usuario (Productor) vinculado correctamente.
        if (pedido.getProducto() != null && pedido.getProducto().getIdProducto() != null) {
            Producto productoCompleto = productoService.buscarPorId(pedido.getProducto().getIdProducto());
            pedido.setProducto(productoCompleto);
        }

        // Guarda y dispara de manera automatizada la notificación de "Nueva orden"
        pedidoService.guardarPedido(pedido);

        redirectAttributes.addFlashAttribute("mensajeExito", "¡Pedido realizado con éxito!");
        return "redirect:/pedidos";
    }

    // Cambiar estado y/o asignar transportador de forma automatizada
    @GetMapping("/cambiarEstado/{id}/{estado}")
    public String cambiarEstado(
            @PathVariable Integer id,
            @PathVariable String estado,
            @RequestParam(value = "idTransportador", required = false) Integer idTransportador)
        {    
            Pedido pedido = pedidoService.buscarPorId(id);

            if (pedido != null) {
                pedido.setEstado(estado);

                // 🚛 ASIGNACIÓN SEGURA: Traemos el transportador completo usando persistencia nativa
                if (idTransportador != null) {
                    try {
                        // find() mapea al Usuario completo desde la BD usando su ID físico
                        Usuario transportadorAsignado = entityManager.find(Usuario.class, idTransportador);
                        pedido.setTransportador(transportadorAsignado);
                    } catch (Exception e) {
                        System.err.println("Error obteniendo el transportador por JPA: " + e.getMessage());
                    }
                }

                // Procesamos la actualización que dispara las alertas automáticas
                pedidoService.actualizarPedido(pedido);
            }

            return "redirect:/pedidos";
        }
    
    @GetMapping("/eliminarPedido/{id}")
    public String eliminarPedido(@PathVariable Integer id) {
        pedidoService.eliminarPedido(id);
        return "redirect:/pedidos";
    }
}

