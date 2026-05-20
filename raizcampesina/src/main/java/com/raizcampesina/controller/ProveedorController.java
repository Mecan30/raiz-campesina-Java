
package com.raizcampesina.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Controlador enfocado en el manejo del panel institucional del Proveedor/Productor.
@Controller
public class ProveedorController
    {
        @GetMapping("/proveedor")
        public String mostrarProveedor()
            {
                return "proveedor";
            }
    }

