
package com.raizcampesina.service;

import com.raizcampesina.model.Mensaje;
import com.raizcampesina.repository.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// Servicio encargado de soportar la mensajería en tiempo real dentro del chat.
@Service
public class MensajeService
    {
        @Autowired
        private MensajeRepository mensajeRepository;

        // Guardar mensaje
        public void guardarMensaje(Mensaje mensaje)
            {
                mensaje.setFechaMensaje(LocalDateTime.now());

                mensajeRepository.save(mensaje);
            }

        // Listar mensajes
        public List<Mensaje> listarMensajes()
            {
                return mensajeRepository.findAll();
            }

        // Buscar por ID
        public Mensaje buscarPorId(Integer id)
            {
                return mensajeRepository.findById(id).orElse(null);
            }

        // Eliminar mensaje
        public void eliminarMensaje(Integer id)
            {
                mensajeRepository.deleteById(id);
            }
        
        // Recupera de manera segmentada los textos vinculados a un solo negocio o flete.
        public List<Mensaje> buscarPorPedido(Integer idPedido)
            {
                return mensajeRepository.findByPedidoIdPedido(idPedido);
            }
    }

