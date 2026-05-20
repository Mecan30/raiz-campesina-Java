
package com.raizcampesina.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private Integer idNotificacion;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    // Cambiado para acoplarse a tu base de datos real
    private String titulo;
    private String descripcion;

    private Boolean leida = false;

    @Column(name = "fecha_notificacion")
    private LocalDateTime fechaNotificacion;

    // El nuevo atributo para el borrado lógico que acabamos de inyectar con el ALTER TABLE
    @Column(name = "visible_usuario", nullable = false)
    private Boolean visibleUsuario = true;

    public Notificacion() {}

    public Integer getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Integer idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getLeida() {
        return leida;
    }

    public void setLeida(Boolean leida) {
        this.leida = leida;
    }

    public LocalDateTime getFechaNotificacion() {
        return fechaNotificacion;
    }

    public void setFechaNotificacion(LocalDateTime fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    public Boolean getVisibleUsuario() {
        return visibleUsuario;
    }

    public void setVisibleUsuario(Boolean visibleUsuario) {
        this.visibleUsuario = visibleUsuario;
    }
}
