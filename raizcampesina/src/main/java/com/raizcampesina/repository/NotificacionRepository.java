
package com.raizcampesina.repository;

import com.raizcampesina.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer>
    {    
        // Filtra las notificaciones de un usuario específico pero solo si NO las ha ocultado de su pantalla
        List<Notificacion> findByUsuarioIdUsuarioAndVisibleUsuarioTrue(Integer idUsuario);
    }