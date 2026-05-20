
package com.raizcampesina.repository;

import com.raizcampesina.model.Favorito;
import com.raizcampesina.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repositorio encargado del acceso a los datos de la tabla de Favoritos.
@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Integer>
    {
        // Recupera de manera automatizada la lista de productos preferidos asociados a un consumidor específico.
        List<Favorito> findByUsuario(Usuario usuario);

    }
