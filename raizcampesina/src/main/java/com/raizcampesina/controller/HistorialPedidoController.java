
package com.raizcampesina.controller;

import com.raizcampesina.model.HistorialPedido;
import com.raizcampesina.model.Pedido;
import com.raizcampesina.model.Usuario;
import com.raizcampesina.service.HistorialPedidoService;
import com.raizcampesina.service.PedidoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador enfocado en auditar y renderizar los estados de trazabilidad de los pedidos.
// Segmenta la visibilidad de la información según las restricciones de cada Rol.
@Controller
public class HistorialPedidoController {

    @Autowired
    private HistorialPedidoService historialPedidoService;

    @Autowired
    private PedidoService pedidoService;

    // Mostrar historial FILTRADO por Rol de usuario
    @GetMapping("/historial")
    public String mostrarHistorial(HttpSession session, Model model) {

        // Recuperamos al usuario que inició sesión
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null)
            {
                return "redirect:/login"; 
            }

        List<Pedido> pedidosFiltrados;
        String rol = usuarioLogueado.getRol().getNombreRol();

        // Aplicamos la segmentación de datos por Rol
        if (rol.equals("CONSUMIDOR"))
            {
                pedidosFiltrados = pedidoService.buscarPorConsumidor(usuarioLogueado.getIdUsuario());

            }
        else if (rol.equals("PROVEEDOR"))
            {
                pedidosFiltrados = pedidoService.buscarPorProveedor(usuarioLogueado.getIdUsuario());

            }
        else if (rol.equals("TRANSPORTADOR"))
            {
                // Obtenemos todos losp edidos asignados.
                List<Pedido> todosSusPedidos = pedidoService.buscarPorTransportador(usuarioLogueado.getIdUsuario());

                // Filtramos en tiempo real solo los que ya fueron exitosamente entregados.
                pedidosFiltrados = todosSusPedidos.stream()
                    .filter(p -> p.getEstado() != null && p.getEstado().equalsIgnoreCase("ENTREGADO"))
                    .toList();
            }
        else
            {
                // Administrador o vista por defecto
                pedidosFiltrados = pedidoService.listarPedidos();
            }

        // Enviamos los atributos correspondientes al modelo HTML
        model.addAttribute("historial", new HistorialPedido());
        model.addAttribute("listaHistorial", historialPedidoService.listarHistorial());
        model.addAttribute("listaPedidos", pedidosFiltrados);
        model.addAttribute("rolUsuario", rol); 

        return "historial";
    }

    // Guardar historial
    @PostMapping("/guardarHistorial")
    public String guardarHistorial(@ModelAttribute HistorialPedido historial) {
        historialPedidoService.guardarHistorial(historial);
        return "redirect:/historial";
    }

    // Eliminar historial
    @GetMapping("/eliminarHistorial/{id}")
    public String eliminarHistorial(@PathVariable Integer id) {
        historialPedidoService.eliminarHistorial(id);
        return "redirect:/historial";
    }
    
    // API endpoint interna que permite al transportador cambiar en tiempo real la fase de un envío.
    @PostMapping("/pedidos/actualizar-estado")
    public String actualizarEstadoPedido(@RequestParam("idPedido") Integer idPedido, 
                                         @RequestParam("nuevoEstado") String nuevoEstado,
                                         HttpSession session)
        {
            Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
            
            // Bloqueo de seguridad: Validamos que quien ejecute el cambio sea un transportador con credenciales vigentes.
            if (usuarioLogueado == null || !usuarioLogueado.getRol().getNombreRol().equals("TRANSPORTADOR"))
                {
                    return "redirect:/login";
                }

            Pedido pedido = pedidoService.buscarPorId(idPedido);

            if (pedido != null)
                {
                    // Homologamos a mayúsculas para mantener consistencia limpia en la persistencia de MySQL
                    pedido.setEstado(nuevoEstado.toUpperCase());
                    pedidoService.actualizarPedido(pedido); 
                }

            return "redirect:/logistica";
        }
}

