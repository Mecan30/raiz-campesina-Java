
package com.raizcampesina.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// Entidad que mapea el chat o mensajería interna del sistema.
// Permite la comunicación directa vinculada a un pedido específico.
@Entity
@Table(name = "mensajes")
public class Mensaje
    {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer idMensaje;

        @ManyToOne
        @JoinColumn(name = "id_remitente")
        private Usuario remitente;

        @ManyToOne
        @JoinColumn(name = "id_destinatario")
        private Usuario destinatario;

        // Pedido al cual está ligado el hilo de conversación.
        @ManyToOne
        @JoinColumn(name = "id_pedido")
        private Pedido pedido;
        
        @Column(name = "mensaje")
        private String contenido;

        private LocalDateTime fechaMensaje;

        public Mensaje()
            {}

        public Integer getIdMensaje()
            {
                return idMensaje;
            }

        public void setIdMensaje(Integer idMensaje)
            {
                this.idMensaje = idMensaje;
            }

        public Usuario getRemitente()
            {
                return remitente;
            }

        public void setRemitente(Usuario remitente)
            {
                this.remitente = remitente;
            }

        public Usuario getDestinatario()
            {
                return destinatario;
            }

        public void setDestinatario(Usuario destinatario)
            {
                this.destinatario = destinatario;
            }

        public Pedido getPedido()
            {
                return pedido;
            }

        public void setPedido(Pedido pedido)
            {
                this.pedido = pedido;
            }

        public String getContenido()
            {
                return contenido;
            }

        public void setContenido(String contenido)
            {
                this.contenido = contenido;
            }

        public LocalDateTime getFechaMensaje()
            {
                return fechaMensaje;
            }

        public void setFechaMensaje(LocalDateTime fechaMensaje)
            {
                this.fechaMensaje = fechaMensaje;
            }
    }

