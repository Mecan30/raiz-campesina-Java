
package com.raizcampesina.controller;

import com.raizcampesina.model.Notificacion;
import com.raizcampesina.model.Usuario;
import com.raizcampesina.service.NotificacionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping("/notificaciones")
    public String mostrarNotificaciones(Model model, HttpSession session) {
        String rol = (String) session.getAttribute("rolUsuario");
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (rol == null || usuarioLogueado == null) {
            return "redirect:/";
        }

        List<Notificacion> lista;

        if (rol.equals("ADMINISTRADOR")) {
            // El administrador ve TODO el censo de alertas (auditoría pura)
            lista = notificacionService.listarNotificaciones();
        } else {
            // Los usuarios normales solo ven sus alertas que no hayan ocultado
            lista = notificacionService.buscarPorUsuarioYVisibles(usuarioLogueado);
        }

        model.addAttribute("listaNotificaciones", lista);
        model.addAttribute("rolUsuario", rol);

        return "notificaciones";
    }

    // Acción de "Borrado Lógico" desde la interfaz del usuario
    @GetMapping("/eliminarNotificacion/{id}")
    public String eliminarNotificacion(@PathVariable Integer id, HttpSession session) {
        String rol = (String) session.getAttribute("rolUsuario");
        
        if (rol == null) {
            return "redirect:/";
        }

        Notificacion noti = notificacionService.buscarPorId(id);
        if (noti != null) {
            if (rol.equals("ADMINISTRADOR")) {
                // Si es el Administrador, borra el registro real de MySQL (opcional)
                notificacionService.eliminarDefinitivamente(id);
            } else {
                // Si es un usuario común, SOLO SE CAMBIA EL ESTADO para ocultarlo de su pantalla
                noti.setVisibleUsuario(false);
                notificacionService.guardarNotificacion(noti); 
            }
        }

        return "redirect:/notificaciones";
    }

    @PostMapping("/guardarNotificacion")
    public String guardarNotificacion(@ModelAttribute Notificacion notificacion) {
        notificacionService.guardarNotificacion(notificacion);
        return "redirect:/notificaciones";
    }

    @GetMapping("/leerNotificacion/{id}")
    public String leerNotificacion(@PathVariable Integer id) {
        notificacionService.marcarComoLeida(id);
        return "redirect:/notificaciones";
    }
}

