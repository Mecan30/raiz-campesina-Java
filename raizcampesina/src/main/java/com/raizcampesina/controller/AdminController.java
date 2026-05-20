
package com.raizcampesina.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

 // Controlador para la gestión de las rutas del módulo administrativo.
 // Centraliza el acceso a las vistas de control global del sistema.    

@Controller
public class AdminController
    {
        @GetMapping("/admin")
        public String mostrarAdmin()
            {
                return "admin";
            }
    }