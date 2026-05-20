
package com.raizcampesina.controller;

import com.raizcampesina.model.Usuario;
import com.raizcampesina.service.UsuarioService;
import com.raizcampesina.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// Controlador encargado del sistema de autenticación, registro y control de sesiones de los usuarios en la plataforma. 

@Controller
public class AuthController
    {
        @Autowired
        private UsuarioService usuarioService;
        
        @Autowired
        private RolService rolService;

        // Carga la página de bienvenida (Landing Page).
        // Prepara un objeto de usuario vacío y la lista de roles permitidos para poblar el formulario de registro.
        @GetMapping("/")
        public String inicio(Model model)
            {
                model.addAttribute("usuario", new Usuario());
                // Cargamos los roles específicos que se pueden registrar públicamente (Proveedor, Consumidor, Transportador)
                model.addAttribute("listaRoles",
                        rolService.listarRolesRegistro());

                return "inicio";
            }
     
        // Procesa el formulario de registro de un nuevo usuario a través del servicio de negocio.
        @PostMapping("/registrar")
        public String registrarUsuario(
                @ModelAttribute Usuario usuario,
                Model model)
            {
                // Ejecuta la lógica de inserción y retorna el mensaje de éxito o validación de duplicados.
                String resultado = usuarioService.registrarUsuario(usuario);
                model.addAttribute("msg", resultado);
                
                // Limpiamos el formulario y refrescamos los roles para la interfaz.
                model.addAttribute("usuario", new Usuario());
                model.addAttribute("listaRoles",
                    rolService.listarRolesRegistro());

                return "inicio";
            }

        // Valida las credenciales de acceso del usuario e inicia la sesión HTTP guardando sus datos clave.
        @PostMapping("/login")
        public String login(
                @RequestParam String correo,
                @RequestParam String clave,
                Model model,
                jakarta.servlet.http.HttpSession session)
            {
                Usuario usuario = usuarioService.validarLogin(correo, clave);

                if (usuario != null)
                    {
                        // Registramos las variables globales en la sesión del servidor para usarlas como filtros.
                        session.setAttribute("usuarioLogueado", usuario);
                        session.setAttribute("nombreUsuario", usuario.getNombre());
                        session.setAttribute("rolUsuario",
                                usuario.getRol().getNombreRol());

                        // Redirección intermedia hacia el enrutador de paneles según el rol.
                        return "redirect:/panel";
                    }
                else
                    {
                        // Si las credenciales fallan, notificamos al usuario sin levantar sesión.
                        model.addAttribute("msg", "errorLogin");
                        model.addAttribute("usuario", new Usuario());

                        return "inicio";
                    }
            }
        
        // Destruye de forma segura la sesión activa del usuario y limpia la memoria del servidor.
        @GetMapping("/logout")
        public String logout(
                jakarta.servlet.http.HttpSession session)
            {
                session.invalidate();

                return "redirect:/";
            }
    }


