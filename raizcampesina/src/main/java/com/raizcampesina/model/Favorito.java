
package com.raizcampesina.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// Entidad que representa la tabla intermedia de productos favoritos.
// Registra el interés de un consumidor por la cosecha de un proveedor.
@Entity
@Table(name = "favoritos")
public class Favorito
    {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer idFavorito;

        // Relación de muchos a uno: Varios favoritos pueden pertenecer al mismo usuario.
        @ManyToOne
        @JoinColumn(name = "id_usuario")
        private Usuario usuario;

        // Relación de muchos a uno: Varios registros de favoritos pueden apuntar al mismo producto.
        @ManyToOne
        @JoinColumn(name = "id_producto")
        private Producto producto;

        private LocalDateTime fechaFavorito;

        public Favorito()
            {}

        public Integer getIdFavorito()
            {
                return idFavorito;
            }

        public void setIdFavorito(Integer idFavorito)
            {
                this.idFavorito = idFavorito;
            }

        public Usuario getUsuario()
            {
                return usuario;
            }

        public void setUsuario(Usuario usuario)
            {
                this.usuario = usuario;
            }

        public Producto getProducto()
            {
                return producto;
            }

        public void setProducto(Producto producto)
            {
                this.producto = producto;
            }

        public LocalDateTime getFechaFavorito()
            {
                return fechaFavorito;
            }

        public void setFechaFavorito(LocalDateTime fechaFavorito)
            {
                this.fechaFavorito = fechaFavorito;
            }
    }

