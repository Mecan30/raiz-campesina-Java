
package com.raizcampesina.repository;

import com.raizcampesina.model.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// Repositorio encargado de gestionar las consultas de la mensajería o chat interno.
@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Integer>
    {
        // Obtener todos los mensajes asociados estrictamente a un pedido específico.
        List<Mensaje> findByPedidoIdPedido(Integer idPedido);

        // Opcional: Obtener la conversación privada y directa entre dos usuarios en un pedido.
        List<Mensaje> findByPedidoIdPedidoAndRemitenteIdUsuarioAndDestinatarioIdUsuarioOrPedidoIdPedidoAndRemitenteIdUsuarioAndDestinatarioIdUsuarioOrderByFechaMensajeAsc(
            Integer idPedido1, Integer idRemitente1, Integer idDestinatario1,
            Integer idPedido2, Integer idRemitente2, Integer idDestinatario2
        );
    }

