
package com.raizcampesina.service;

import com.raizcampesina.model.Valoracion;
import com.raizcampesina.repository.ValoracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import java.util.Optional;

// Servicio encargado de gestionar las calificaciones y opiniones sobre la calidad del servicio.
@Service
public class ValoracionService
    {
        @Autowired
        private ValoracionRepository valoracionRepository;

        // Guardar valoración
        public void guardarValoracion(Valoracion nuevaValoracion)
        {
            // Buscamos si este consumidor ya calificó a este usuario específico en este pedido
            Optional<Valoracion> valoracionExistente = valoracionRepository.findByPedidoAndConsumidorAndUsuario(
                nuevaValoracion.getPedido(), 
                nuevaValoracion.getConsumidor(), 
                nuevaValoracion.getUsuario()
            );

            if (valoracionExistente.isPresent()) {
                // Si ya existe, extraemos el registro de la BD y lo actualizamos
                Valoracion valoracionAActualizar = valoracionExistente.get();
                valoracionAActualizar.setCalificacion(nuevaValoracion.getCalificacion());
                valoracionAActualizar.setComentario(nuevaValoracion.getComentario());
                valoracionAActualizar.setFechaValoracion(java.time.LocalDateTime.now()); // Actualiza la fecha de edición

                // Guardará el registro existente manteniendo su mismo IDValoracion (hace un UPDATE)
                valoracionRepository.save(valoracionAActualizar);
            } else {
                // Si no existe, guarda el objeto nuevo tal cual (hace un INSERT)
                nuevaValoracion.setFechaValoracion(java.time.LocalDateTime.now());
                valoracionRepository.save(nuevaValoracion);
            }
        }

        // Listar valoraciones
        public List<Valoracion> listarValoraciones()
            {
                return valoracionRepository.findAll();
            }

        // Buscar por ID
        public Valoracion buscarPorId(Integer id)
            {
                return valoracionRepository.findById(id).orElse(null);
            }

        // Eliminar valoración
        public void eliminarValoracion(Integer id)
            {
                valoracionRepository.deleteById(id);
            }
    }

