
package com.raizcampesina.repository;

import com.raizcampesina.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

// Repositorio encargado de la gestión de órdenes de compra y fletes en el sistema.
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>
    {
        // Busca los pedidos donde el usuario comprador (mapeado ahora a id_consumidor) coincida
        @Query("SELECT p FROM Pedido p WHERE p.usuario.idUsuario = :idUsuario")
        List<Pedido> buscarPedidosPorConsumidor(@Param("idUsuario") Integer idUsuario);

        // Busca los pedidos asignados al transportador
        @Query("SELECT p FROM Pedido p WHERE p.transportador.idUsuario = :idUsuario")
        List<Pedido> buscarPedidosPorTransportador(@Param("idUsuario") Integer idUsuario);

        // Busca los pedidos cuyo producto pertenezca al proveedor logueado
        @Query("SELECT p FROM Pedido p WHERE p.producto.usuario.idUsuario = :idUsuario")
        List<Pedido> buscarPedidosPorProveedor(@Param("idUsuario") Integer idUsuario);
    }