
package com.raizcampesina.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// Entidad núcleo del flujo comercial de la plataforma.
// Conecta al consumidor (comprador), el producto (cosecha) y al transportador asignado.
@Entity
@Table(name = "pedidos")
public class Pedido
    {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_pedido")
        private Integer idPedido;
        
        // Consumidor que efectúa la orden de compra.
        @ManyToOne
        @JoinColumn(name = "id_consumidor")
        private Usuario usuario;

        @ManyToOne
        @JoinColumn(name = "id_producto")
        private Producto producto;
        
        // Transportador encargado de realizar la entrega física de la mercancía.
        @ManyToOne
        @JoinColumn(name = "id_transportador")
        private Usuario transportador;

        private Integer cantidad;
        private Double total;
        private String estado;

        @Column(name = "fecha_pedido")
        private LocalDateTime fechaPedido;

        public Pedido()
            {}

        public Integer getIdPedido()
            {
                return idPedido;
            }

        public void setIdPedido(Integer idPedido)
            {
                this.idPedido = idPedido;
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

        public Usuario getTransportador()
            {
                return transportador;
            }

        public void setTransportador(Usuario transportador)
            {
                this.transportador = transportador;
            }
        
        public Integer getCantidad()
            {
                return cantidad;
            }

        public void setCantidad(Integer cantidad)
            {
                this.cantidad = cantidad;
            }

        public Double getTotal()
            {
                return total;
            }

        public void setTotal(Double total)
            {
                this.total = total;
            }

        public String getEstado()
            {
                return estado;
            }

        public void setEstado(String estado)
            {
                this.estado = estado;
            }

        public LocalDateTime getFechaPedido()
            {
                return fechaPedido;
            }

        public void setFechaPedido(LocalDateTime fechaPedido)
            {
                this.fechaPedido = fechaPedido;
            }
    }

