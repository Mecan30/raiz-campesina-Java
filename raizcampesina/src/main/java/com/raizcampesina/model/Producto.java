
package com.raizcampesina.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// Entidad que representa las cosechas u ofertas comerciales de los agricultores.
// Contiene una propiedad transitoria para calcular favoritos dinámicamente.
@Entity
@Table(name = "productos")
public class Producto
    {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_producto")
        private Integer idProducto;

        private String nombre;
        private String descripcion;
        private Double precio;
        private Integer stock;
        private Boolean estado;
        private String imagen;
        
        // Campo transitorio: No mapea en las columnas de MySQL. Se evalúa bajo lógica del Controller.
        @Transient
        private Boolean favorito = false;

        @Column(name = "fecha_publicacion")
        private LocalDateTime fechaPublicacion;

        // Usuario proveedor que publica
        @ManyToOne
        @JoinColumn(name = "id_proveedor")
        private Usuario usuario;

        public Producto()
            {}

        public Integer getIdProducto()
            {
                return idProducto;
            }

        public void setIdProducto(Integer idProducto)
            {
                this.idProducto = idProducto;
            }

        public String getNombre()
            {
                return nombre;
            }

        public void setNombre(String nombre)
            {
                this.nombre = nombre;
            }

        public String getDescripcion()
            {
                return descripcion;
            }

        public void setDescripcion(String descripcion)
            {
                this.descripcion = descripcion;
            }

        public Double getPrecio()
            {
                return precio;
            }

        public void setPrecio(Double precio)
            {
                this.precio = precio;
            }

        public Integer getStock()
            {
                return stock;
            }

        public void setStock(Integer stock)
            {
                this.stock = stock;
            }

        public Boolean getEstado()
            {
                return estado;
            }

        public void setEstado(Boolean estado)
            {
                this.estado = estado;
            }

        public LocalDateTime getFechaPublicacion()
            {
                return fechaPublicacion;
            }

        public void setFechaPublicacion(LocalDateTime fechaPublicacion)
            {
                this.fechaPublicacion = fechaPublicacion;
            }

        public Usuario getUsuario()
            {
                return usuario;
            }

        public void setUsuario(Usuario usuario)
            {
                this.usuario = usuario;
            }
        
        public String getImagen()
            {
                return imagen;
            }

        public void setImagen(String imagen)
            {
                this.imagen = imagen;
            }
        
        public Boolean getFavorito()
            {
                return favorito;
            }

        public void setFavorito(Boolean favorito)
            {
                this.favorito = favorito;
            }
    }