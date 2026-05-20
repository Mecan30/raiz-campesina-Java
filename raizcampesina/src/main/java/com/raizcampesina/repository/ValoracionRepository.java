
package com.raizcampesina.repository;

import com.raizcampesina.model.Valoracion;
import com.raizcampesina.model.Pedido;
import com.raizcampesina.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

// Repositorio para la administración de las calificaciones y opiniones de las entregas.
@Repository
public interface ValoracionRepository extends JpaRepository<Valoracion, Integer>
    {
        // Busca si ya existe una valoración con este pedido, consumidor y usuario calificado
        Optional<Valoracion> findByPedidoAndConsumidorAndUsuario(Pedido pedido, Usuario consumidor, Usuario usuario);
    }