
package com.raizcampesina.controller;

import com.raizcampesina.model.Mensaje;
import com.raizcampesina.service.MensajeService;
import com.raizcampesina.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import com.raizcampesina.model.Pedido;
import java.util.List;
import java.util.ArrayList;

import com.raizcampesina.service.PedidoService;
import com.raizcampesina.model.Usuario;

// Controlador para la mensajería interna y salas de chat bilaterales.
// Permite la comunicación directa entre Consumidor, Agricultor y Transportador por cada flete/pedido.
@Controller
public class MensajeController
    {
        @Autowired
        private MensajeService mensajeService;

        @Autowired
        private UsuarioService usuarioService;

        @Autowired
        private PedidoService pedidoService;
        
        // Mostrar mensajes generales
        @GetMapping("/mensajes")
        public String mostrarMensajes(Model model)
            {
                model.addAttribute("mensaje", new Mensaje());
                model.addAttribute("listaMensajes",
                        mensajeService.listarMensajes());
                model.addAttribute("listaUsuarios",
                        usuarioService.listarUsuarios());

                return "mensajes";
            }

        // Mostrar chat por pedido específico
        @GetMapping("/mensajes/{idPedido}")
        public String mostrarChatPedido(
                @PathVariable("idPedido") Integer idPedido,
                @RequestParam(value = "destinatarioId", required = false) Integer destinatarioId,
                HttpSession session, 
                Model model) {

            Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
            if (usuarioLogueado == null) {
                return "redirect:/login";
            }

            // Buscar el pedido actual para conocer a sus miembros reales
            Pedido pedido = pedidoService.buscarPorId(idPedido); 

            // Filtrar y pasar los usuarios involucrados en este pedido específico
            model.addAttribute("proveedorPedido", pedido.getProducto().getUsuario());
            model.addAttribute("transportadorPedido", pedido.getTransportador());
            model.addAttribute("consumidorPedido", pedido.getUsuario());

            // Obtener mensajes ÚNICAMENTE de este pedido
            List<Mensaje> mensajesDelPedido = mensajeService.buscarPorPedido(idPedido);

            // Si hay un destinatario seleccionado, filtramos la conversación bilateral
            List<Mensaje> mensajesFiltrados = new ArrayList<>();
            if (destinatarioId != null) {
                mensajesFiltrados = mensajesDelPedido.stream()
                    .filter(m -> (m.getRemitente().getIdUsuario().equals(usuarioLogueado.getIdUsuario()) && m.getDestinatario().getIdUsuario().equals(destinatarioId))
                              || (m.getRemitente().getIdUsuario().equals(destinatarioId) && m.getDestinatario().getIdUsuario().equals(usuarioLogueado.getIdUsuario())))
                    .toList();
            } else {
                // Si no ha seleccionado a nadie, mostramos todo el histórico del pedido o lo dejamos vacío
                mensajesFiltrados = mensajesDelPedido;
            }

            // Inyectar datos al modelo HTML
            model.addAttribute("idPedido", idPedido);
            model.addAttribute("destinatarioSeleccionadoId", destinatarioId);
            model.addAttribute("listaMensajes", mensajesFiltrados);
            model.addAttribute("mensaje", new Mensaje()); // Para el formulario objeto

            return "mensajes";
        }

        // Guardar mensaje
        @PostMapping("/guardarMensaje")
        public String guardarMensaje(@ModelAttribute("mensaje") Mensaje mensaje, HttpSession session)
            {
                // Asignamos la fecha y hora reales de envío
                mensaje.setFechaMensaje(java.time.LocalDateTime.now());

                // Guardamos en la base de datos de MySQL
                mensajeService.guardarMensaje(mensaje);

                // REDIRECCIÓN LIMPIA (PRG): Limpia la petición POST del navegador para que no se duplique.
                // Te mantiene en la misma pantalla cargando los parámetros desde cero en una petición GET limpia.
                return "redirect:/mensajes/" + mensaje.getPedido().getIdPedido() 
                        + "?destinatarioId=" + mensaje.getDestinatario().getIdUsuario();
            }

        // Elminar mensaje
        @GetMapping("/eliminarMensaje/{id}")
        public String eliminarMensaje(@PathVariable("id") Integer idMensaje)
            {
                // Antes de borrar, recuperamos el mensaje para saber de qué pedido y destinatario era.
                Mensaje mensajeAEliminar = mensajeService.buscarPorId(idMensaje);
                Integer idPedido = mensajeAEliminar.getPedido().getIdPedido();
                Integer idDestinatario = mensajeAEliminar.getDestinatario().getIdUsuario();

                // Procedemos a eliminarlo físicamente de la base de datos.
                mensajeService.eliminarMensaje(idMensaje);

                // REDIRECCIÓN INTELIGENTE: Al usar "redirect", obligamos a pasar de nuevo por el 
                // método GET que carga el pedido, trayendo de vuelta todos tus botones y formularios intactos.
                return "redirect:/mensajes/" + idPedido + "?destinatarioId=" + idDestinatario;
            }
    }

