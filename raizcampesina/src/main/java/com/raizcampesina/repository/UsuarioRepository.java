
package com.raizcampesina.repository;

import com.raizcampesina.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

// Repositorio central de credenciales, cuentas y datos personales de los usuarios.
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>
    {
        // Busca una cuenta en el sistema evaluando el correo electrónico (Útil en procesos de login).
        Optional<Usuario> findByCorreo(String correo);

        // Autenticación estricta: Cruza de manera directa el correo y la contraseña ingresada en el inicio de sesión.
        Optional<Usuario> findByCorreoAndClave(String correo, String clave);

        // Validación de unicidad: Comprueba si un correo ya se encuentra en uso antes de permitir un registro.
        boolean existsByCorreo(String correo);

        // Validación de identidad: Evita que dos personas se registren con el mismo número de cédula o documento.
        boolean existsByNumeroIdentificacion(String numeroIdentificacion);
        
        // Consulta avanzada con lenguaje JPQL: Realiza un JOIN implícito para extraer un listado de usuarios que cumplan estrictamente con un Rol determinado.
        @Query("SELECT u FROM Usuario u WHERE u.rol.nombreRol = :nombreRol")
        List<Usuario> buscarPorNombreRol(@Param("nombreRol") String nombreRol);
    }

