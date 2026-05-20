
package com.raizcampesina.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Controlador encargado de gestionar el acceso al panel privado de los clientes o consumidores finales.
@Controller
public class ConsumidorController
    {   
        // Renderiza la interfaz principal del consumidor.
        @GetMapping("/consumidor")
        public String mostrarConsumidor()
            {
                return "consumidor";
            }
    }

