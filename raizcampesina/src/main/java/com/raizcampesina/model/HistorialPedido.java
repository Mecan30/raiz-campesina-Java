
package com.raizcampesina.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// Entidad encargada de auditar la trazabilidad y cambios de estado de un pedido.
// Almacena el histórico cronológico de los despachos en la plataforma.
@Entity
@Table(name = "historial_pedidos")
public class HistorialPedido
    {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer idHistorial;

        @ManyToOne
        @JoinColumn(name = "id_pedido")
        private Pedido pedido;

        private String estadoAnterior;

        private String estadoNuevo;

        private LocalDateTime fechaCambio;

        public HistorialPedido()
            {}

        public Integer getIdHistorial()
            {
                return idHistorial;
            }

        public void setIdHistorial(Integer idHistorial)
            {
                this.idHistorial = idHistorial;
            }

        public Pedido getPedido()
            {
                return pedido;
            }

        public void setPedido(Pedido pedido)
            {
                this.pedido = pedido;
            }

        public String getEstadoAnterior()
            {
                return estadoAnterior;
            }

        public void setEstadoAnterior(String estadoAnterior)
            {
                this.estadoAnterior = estadoAnterior;
            }

        public String getEstadoNuevo()
            {
                return estadoNuevo;
            }

        public void setEstadoNuevo(String estadoNuevo)
            {
                this.estadoNuevo = estadoNuevo;
            }

        public LocalDateTime getFechaCambio()
            {
                return fechaCambio;
            }

        public void setFechaCambio(LocalDateTime fechaCambio)
            {
                this.fechaCambio = fechaCambio;
            }
    }

