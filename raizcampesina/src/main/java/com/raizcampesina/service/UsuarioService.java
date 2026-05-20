
package com.raizcampesina.service;

import com.raizcampesina.model.Usuario;
import com.raizcampesina.repository.RolRepository;
import com.raizcampesina.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

// Servicio central para el control de cuentas, credenciales, accesos y altas de usuarios.
@Service
public class UsuarioService
    {
        @Autowired
        private UsuarioRepository usuarioRepository;

        @Autowired
        private RolRepository rolRepository;

        // Valida la unicidad de datos y registra de manera formal un nuevo actor en la plataforma.
        public String registrarUsuario(Usuario usuario)
            {
                // VALIDACIÓN DE SEGURIDAD 1: Comprobación de duplicados de correo electrónico.
                if (usuarioRepository.existsByCorreo(usuario.getCorreo()))
                    {
                        return "correoExiste";
                    }
                
                // VALIDACIÓN DE SEGURIDAD 2: Comprobación de duplicados en el documento de identidad.
                if (usuarioRepository.existsByNumeroIdentificacion(
                        usuario.getNumeroIdentificacion()))
                            {
                                return "identificacionExiste";
                            }

                // Rol por defecto
                usuario.setEstado(true);
                usuario.setFechaRegistro(LocalDateTime.now());

                usuarioRepository.save(usuario);

                return "ok";
            }

        // Valida de manera estricta las credenciales interceptadas en el formulario de acceso.
        public Usuario validarLogin(String correo, String clave)
            {
                Optional<Usuario> usuario = usuarioRepository
                        .findByCorreoAndClave(correo, clave);

                return usuario.orElse(null);
            }
        
        // Listar usuarios
        public List<Usuario> listarUsuarios()
            {
                return usuarioRepository.findAll();
            }
        
        public List<Usuario> obtenerUsuariosPorRol(String rol)
            {            
                return usuarioRepository.buscarPorNombreRol(rol); 
            }
    }

