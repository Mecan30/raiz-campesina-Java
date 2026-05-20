
package com.raizcampesina.service;

import com.raizcampesina.model.Favorito;
import com.raizcampesina.repository.FavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// Servicio encargado de gestionar las preferencias de productos del consumidor.
@Service
public class FavoritoService
    {
        @Autowired
        private FavoritoRepository favoritoRepository;

        // Guardar favorito
        public void guardarFavorito(Favorito favorito)
            {
                favorito.setFechaFavorito(LocalDateTime.now());

                favoritoRepository.save(favorito);
            }

        // Listar favoritos
        public List<Favorito> listarFavoritos()
            {
                return favoritoRepository.findAll();
            }

        // Buscar por ID
        public Favorito buscarPorId(Integer id)
            {
                return favoritoRepository.findById(id).orElse(null);
            }

        // Eliminar favorito
        public void eliminarFavorito(Integer id)
            {
                favoritoRepository.deleteById(id);
            }
    }

