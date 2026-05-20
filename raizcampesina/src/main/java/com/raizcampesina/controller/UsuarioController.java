
package com.raizcampesina.controller;

import com.raizcampesina.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// Controlador administrativo encargado de la auditoría masiva de cuentas de usuarios del sistema.
@Controller
public class UsuarioController
    {
        @Autowired
        private UsuarioService usuarioService;

        @GetMapping("/usuarios")
        public String mostrarUsuarios(Model model)
            {
                model.addAttribute("listaUsuarios",
                        usuarioService.listarUsuarios());

                return "usuarios";
            }
    }

