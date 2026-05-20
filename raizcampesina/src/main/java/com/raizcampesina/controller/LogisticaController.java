
package com.raizcampesina.controller;

import com.raizcampesina.model.Pedido;
import com.raizcampesina.model.Usuario;
import com.raizcampesina.service.PedidoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

// Controlador enfocado exclusivamente en las operaciones de la hoja de ruta y distribución activa para el gremio de Transportadores.
@Controller
public class LogisticaController
    {
        @Autowired
        private PedidoService pedidoService;

        // Construye la vista de fletes activos (Pendientes de entrega) del transportador en sesión.
        @GetMapping("/logistica")
        public String mostrarLogistica(HttpSession session, Model model) {
            Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
            if (usuarioLogueado == null || !usuarioLogueado.getRol().getNombreRol().equals("TRANSPORTADOR")) {
                return "redirect:/login";
            }

            // Obtenemos todos sus pedidos asignados
            List<Pedido> todosSusPedidos = pedidoService.buscarPorTransportador(usuarioLogueado.getIdUsuario());

            // FILTRO CLAVE: Dejamos SOLO los pedidos cuyo estado NO sea "ENTREGADO"
            List<Pedido> pedidosActivos = todosSusPedidos.stream()
                .filter(p -> p.getEstado() != null && !p.getEstado().equalsIgnoreCase("ENTREGADO"))
                .toList();

            // Enviamos la lista limpia a la vista de logística
            model.addAttribute("listaPedidos", pedidosActivos);
            model.addAttribute("rolUsuario", "TRANSPORTADOR");

            return "logistica";
        }
    }

