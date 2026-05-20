
package com.raizcampesina.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Controlador enfocado en el manejo del panel de bienvenida para el sector logístico de transporte.
@Controller
public class TransportadorController
    {
        @GetMapping("/transportador")
        public String mostrarTransportador()
            {
                return "transportador";
            }
    }

