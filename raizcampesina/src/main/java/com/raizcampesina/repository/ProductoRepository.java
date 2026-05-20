
package com.raizcampesina.repository;

import com.raizcampesina.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositorio para la administración y control del inventario de cosechas publicadas.
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>
    {}
// Utiliza las bondades nativas de Spring Data para guardar imágenes, controlar stocks y actualizar precios.