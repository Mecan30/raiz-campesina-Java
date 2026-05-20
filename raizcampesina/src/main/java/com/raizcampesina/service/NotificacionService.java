
package com.raizcampesina.service;

import com.raizcampesina.model.Notificacion;
import com.raizcampesina.model.Usuario;
import com.raizcampesina.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    public List<Notificacion> listarNotificaciones() {
        return notificacionRepository.findAll();
    }

    // 🔥 CORRECCIÓN: Extraemos el ID numérico para evitar conflictos de instancias con la sesión HTTP
    public List<Notificacion> buscarPorUsuarioYVisibles(Usuario usuario) {
        if (usuario == null || usuario.getIdUsuario() == null) {
            return new java.util.ArrayList<>();
        }
        // Llamamos al nuevo método pasando únicamente el entero
        return notificacionRepository.findByUsuarioIdUsuarioAndVisibleUsuarioTrue(usuario.getIdUsuario());
    }

    public Notificacion buscarPorId(Integer id) {
        return notificacionRepository.findById(id).orElse(null);
    }

    public void guardarNotificacion(Notificacion notificacion) {
        notificacionRepository.save(notificacion);
    }

    public void marcarComoLeida(Integer id) {
        Notificacion n = buscarPorId(id);
        if (n != null) {
            n.setLeida(true);
            notificacionRepository.save(n);
        }
    }
    
    public void eliminarDefinitivamente(Integer id) {
        notificacionRepository.deleteById(id);
    }
}
