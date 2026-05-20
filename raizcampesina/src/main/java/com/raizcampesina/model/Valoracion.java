
package com.raizcampesina.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// Entidad encargada de gestionar el módulo de reputación y feedback de calidad.
// Almacena las calificaciones bilaterales ejecutadas al culminar un flete o compra.
@Entity
@Table(name = "valoraciones")
public class Valoracion
    {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer idValoracion;

        // Usuario calificado (puede ser el Proveedor o el Transportador)
        @ManyToOne
        @JoinColumn(name = "id_usuario")
        private Usuario usuario;
        
        // Consumidor que emite el voto de confianza y la reseña.
        @ManyToOne
        @JoinColumn(name = "id_consumidor")
        private Usuario consumidor;

        @ManyToOne
        @JoinColumn(name = "id_producto")
        private Producto producto;

        @ManyToOne
        @JoinColumn(name = "id_pedido")
        private Pedido pedido;
        
        @Column(name = "puntuacion") // 🔥 Mapea con la columna obligatoria de MySQL
        private Integer calificacion;

        private String comentario;

        @Column(name = "fecha_valoracion") // 🔥 Asegura el formato snake_case de la base de datos
        private LocalDateTime fechaValoracion;

        public Valoracion()
            {}

        public Integer getIdValoracion()
            {
                return idValoracion;
            }

        public void setIdValoracion(Integer idValoracion)
            {
                this.idValoracion = idValoracion;
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

        public Pedido getPedido()
            {
                return pedido;
           }

        public void setPedido(Pedido pedido)
            {
                this.pedido = pedido;
            }
        
        public Integer getCalificacion()
            {
                return calificacion;
            }

        public void setCalificacion(Integer calificacion)
            {
                this.calificacion = calificacion;
            }

        public String getComentario()
            {
                return comentario;
            }

        public void setComentario(String comentario)
            {
                this.comentario = comentario;
            }

        public LocalDateTime getFechaValoracion()
            {
                return fechaValoracion;
            }

        public void setFechaValoracion(LocalDateTime fechaValoracion)
            {
                this.fechaValoracion = fechaValoracion;
            }
        
        public Usuario getConsumidor()
            {
                return consumidor;
            }

        public void setConsumidor(Usuario consumidor)
            {
                this.consumidor = consumidor;
            }
    }

