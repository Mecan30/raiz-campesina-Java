
package com.raizcampesina.service;

import com.raizcampesina.model.HistorialPedido;
import com.raizcampesina.repository.HistorialPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// Servicio de auditoría para el seguimiento de los estados de entrega en la cadena logística.
@Service
public class HistorialPedidoService
    {
        @Autowired
        private HistorialPedidoRepository historialPedidoRepository;

        // Guardar historial
        public void guardarHistorial(HistorialPedido historial)
            {
                historial.setFechaCambio(LocalDateTime.now());

                historialPedidoRepository.save(historial);
            }

        // Listar historial
        public List<HistorialPedido> listarHistorial()
            {
                return historialPedidoRepository.findAll();
            }

        // Buscar por ID
        public HistorialPedido buscarPorId(Integer id)
            {
                return historialPedidoRepository.findById(id).orElse(null);
            }

        // Eliminar historial
        public void eliminarHistorial(Integer id)
            {
                historialPedidoRepository.deleteById(id);
            }
    }

