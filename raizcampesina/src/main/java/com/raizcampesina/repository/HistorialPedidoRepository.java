
package com.raizcampesina.repository;

import com.raizcampesina.model.HistorialPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositorio base para la persistencia y auditoría de la trazabilidad de cambios de estados en los despachos.
@Repository
public interface HistorialPedidoRepository extends JpaRepository<HistorialPedido, Integer>
    {}
// Al heredar de JpaRepository, Spring gestiona automáticamente las operaciones CRUD de auditoría.

