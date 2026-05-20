
package com.raizcampesina.service;

import com.raizcampesina.model.Producto;
import com.raizcampesina.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// Servicio encargado de gestionar la vitrina comercial de las cosechas del campo.
@Service
public class ProductoService
    {
        @Autowired
        private ProductoRepository productoRepository;

        // Guardar producto
        public void guardarProducto(Producto producto)
            {
                producto.setEstado(true);
                producto.setFechaPublicacion(LocalDateTime.now());

                productoRepository.save(producto);
            }

        // Listar todos
        public List<Producto> listarProductos()
            {
                return productoRepository.findAll();
            }

        // Buscar por ID
        public Producto buscarPorId(Integer id)
            {
                return productoRepository.findById(id).orElse(null);
            }

        // Eliminar
        public void eliminarProducto(Integer id)
            {
                productoRepository.deleteById(id);
            }
    }