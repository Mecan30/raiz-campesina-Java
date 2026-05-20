
package com.raizcampesina.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// Centralizador de redirección de áreas de trabajo (Dashboard router).
// Verifica quién es el usuario en sesión y lo envía a su interfaz correspondiente.
@Controller
public class PanelController
    {
        @GetMapping("/panel")
        public String mostrarPanel(
                HttpSession session,
                Model model)
            {
                String nombreUsuario =
                        (String) session.getAttribute("nombreUsuario");

                String rolUsuario =
                        (String) session.getAttribute("rolUsuario");

                // Si no hay sesión, vuelve al login
                if (nombreUsuario == null)
                    {
                        return "redirect:/inicio";
                    }

                model.addAttribute("nombreUsuario", nombreUsuario);
                model.addAttribute("rolUsuario", rolUsuario);

                return "panel";
            }
    }

