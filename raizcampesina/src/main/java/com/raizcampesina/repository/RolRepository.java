
package com.raizcampesina.repository;

import com.raizcampesina.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Repositorio encargado de validar las jerarquías de seguridad y perfiles de acceso.
public interface RolRepository extends JpaRepository<Rol, Integer>
    {
        Optional<Rol> findByNombreRol(String nombreRol);
    }

