
package com.raizcampesina.controller;

import com.raizcampesina.model.Valoracion;
import com.raizcampesina.service.ProductoService;
import com.raizcampesina.service.UsuarioService;
import com.raizcampesina.service.ValoracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.raizcampesina.model.Pedido;
import com.raizcampesina.service.PedidoService;

import jakarta.servlet.http.HttpSession;
import com.raizcampesina.model.Usuario;

import java.util.List;

// Controlador encargado del módulo de reputación, calidad y feedback (Sistema de calificaciones).
// Controla la privacidad y exposición de los puntajes obtenidos por proveedores y conductores.
@Controller
public class ValoracionController
    {
        @Autowired
        private ValoracionService valoracionService;

        @Autowired
        private ProductoService productoService;

        @Autowired
        private UsuarioService usuarioService;

        @Autowired
        private PedidoService pedidoService;
        
        // Mostrar valoraciones
        @GetMapping("/valoraciones")
        public String mostrarValoraciones(Model model)
            {
                model.addAttribute("valoracion", new Valoracion());
                model.addAttribute("listaValoraciones",
                        valoracionService.listarValoraciones());
                model.addAttribute("listaProductos",
                        productoService.listarProductos());
                model.addAttribute("listaUsuarios",
                        usuarioService.listarUsuarios());

                return "valoraciones";
            }

        // Despliega la hoja de feedback vinculada a una entrega en concreto, protegiendo las listas por rol.
        @GetMapping("/valoraciones/{id}")
        public String mostrarValoraciones(@PathVariable("id") Integer idPedido, Model model, HttpSession session) {

            // Buscamos el pedido por su ID para extraer sus datos
            Pedido pedido = pedidoService.buscarPorId(idPedido); 

            // Mandamos el objeto de valoración vacío para el formulario th:object
            model.addAttribute("valoracion", new Valoracion());
            model.addAttribute("idPedido", idPedido);

            // Extraemos de forma segura los datos que el HTML necesita
            if (pedido != null) {
                model.addAttribute("productoAsociado", pedido.getProducto());

                // El proveedor es el dueño del producto
                if (pedido.getProducto() != null) {
                    model.addAttribute("proveedorPedido", pedido.getProducto().getUsuario());
                }

                // El transportador asignado al pedido
                model.addAttribute("transportadorPedido", pedido.getTransportador());
            }

            // LÓGICA DE PRIVACIDAD Y FILTRADO
            Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
            List<Valoracion> todasLasValoraciones = valoracionService.listarValoraciones();
            List<Valoracion> listaFiltrada = new java.util.ArrayList<>();

            boolean esConsumidor = false;

            if (usuarioLogueado != null && todasLasValoraciones != null) {

                String nombreRol = "";
                if (usuarioLogueado.getRol() != null) {
                    nombreRol = usuarioLogueado.getRol().getNombreRol(); 
                }

                System.out.println("=== DEBÚGUEO RAÍZ CAMPESINA ===");
                System.out.println("Usuario Logueado ID: " + usuarioLogueado.getIdUsuario() + " | Rol: " + nombreRol);

                if ("Consumidor".equalsIgnoreCase(nombreRol)) {
                    esConsumidor = true; 

                    for (Valoracion v : todasLasValoraciones) {
                        if (v.getPedido() != null && v.getPedido().getIdPedido().equals(idPedido)) {
                            listaFiltrada.add(v);
                        }
                    }
                } else {
                    // Proveedores y Conductores únicamente pueden visualizar las calificaciones que fueron dirigidas a su perfil personal.
                    for (Valoracion v : todasLasValoraciones) {
                        if (v.getPedido() != null && v.getPedido().getIdPedido().equals(idPedido)) {
                            if (v.getUsuario() != null && v.getUsuario().getIdUsuario() != null) {
                                // Comparamos el ID de la base de datos con el ID de la sesión actual
                                if (v.getUsuario().getIdUsuario().intValue() == usuarioLogueado.getIdUsuario().intValue()) {
                                    listaFiltrada.add(v);
                                }
                            }
                        }
                    }
                }
                System.out.println("Cantidad de valoraciones encontradas: " + listaFiltrada.size());
                System.out.println("=================================");
            }

            // Enviamos los datos definitivos a la vista
            model.addAttribute("listaValoraciones", listaFiltrada);
            model.addAttribute("esConsumidor", esConsumidor); 

            return "valoraciones";
        }

        // Guardar valoración
        @PostMapping("/guardarValoracion")
        public String guardarValoracion(@ModelAttribute("valoracion") Valoracion valoracion, 
                                         HttpSession session, 
                                         @RequestParam("pedido.idPedido") Integer idPedido,
                                         @RequestParam(value = "idUsuarioCalificado", required = false) Integer idUsuarioCalificado) {

            // Validamos que venga el usuario calificado seleccionado.
            if (idUsuarioCalificado == null) {
                return "redirect:/valoraciones/" + idPedido;
            }

            // Vinculamos el pedido de forma manual.
            Pedido p = new Pedido();
            p.setIdPedido(idPedido);
            valoracion.setPedido(p);

            // Vinculamos al usuario seleccionado (Proveedor o Transportador).
            Usuario uCalificado = new Usuario();
            uCalificado.setIdUsuario(idUsuarioCalificado);
            valoracion.setUsuario(uCalificado);

            // Vinculamos al Consumidor logueado.
            Usuario consumidorLogueado = (Usuario) session.getAttribute("usuarioLogueado");
            if (consumidorLogueado != null) {
                valoracion.setConsumidor(consumidorLogueado);
            }

            // Sello cronológico imperativo para auditorías de calidad.
            valoracion.setFechaValoracion(java.time.LocalDateTime.now());

            // Guardamos en la base de datos.
            valoracionService.guardarValoracion(valoracion);

            return "redirect:/valoraciones/" + idPedido;
        }

        // Eliminar valoración
        @GetMapping("/eliminarValoracion/{id}/{idPedido}")
        public String eliminarValoracion(@PathVariable("id") Integer idValoracion, 
                                         @PathVariable("idPedido") Integer idPedido)
            {
                valoracionService.eliminarValoracion(idValoracion); 

                return "redirect:/valoraciones/" + idPedido;
            }
    }

