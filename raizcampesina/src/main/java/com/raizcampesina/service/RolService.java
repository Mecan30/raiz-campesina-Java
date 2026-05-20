
package com.raizcampesina.service;

import com.raizcampesina.model.Rol;
import com.raizcampesina.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Servicio encargado de administrar los roles y privilegios de acceso al sistema.
@Service
public class RolService
    {
        @Autowired
        private RolRepository rolRepository;

        // Mostrar roles para registro
        public List<Rol> listarRolesRegistro()
            {
                return rolRepository.findAll()
                        .stream()
                        .filter(rol ->
                                !rol.getNombreRol()
                                        .equals("ADMINISTRADOR"))
                        .toList();
            }
    }

