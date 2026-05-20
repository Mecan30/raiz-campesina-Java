
package com.raizcampesina.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

// Entidad principal para la gestión de cuentas y datos generales de los actores de la plataforma Raíz Campesina.
@Entity
@Table(name = "usuarios")
public class Usuario
    {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_usuario")
        private Integer idUsuario;

        private String nombre;
        private String apellido;

        @Column(name = "numero_identificacion")
        private String numeroIdentificacion;

        private String correo;
        private String clave;
        private String telefono;
        private String departamento;
        private String ciudad;
        private String direccion;

        @Column(name = "fecha_nacimiento")
        private LocalDate fechaNacimiento;

        private String genero;
        private Boolean estado;

        @Column(name = "fecha_registro")
        private LocalDateTime fechaRegistro;

        // Relación foránea directa con el catálogo de roles del sistema.
        @ManyToOne
        @JoinColumn(name = "id_rol")
        private Rol rol;

        public Usuario()
            {}

        public Integer getIdUsuario()
            {
                return idUsuario;
            }

        public void setIdUsuario(Integer idUsuario)
            {
                this.idUsuario = idUsuario;
            }

        public String getNombre()
            {
                return nombre;
            }

        public void setNombre(String nombre)
            {
                this.nombre = nombre;
            }

        public String getApellido()
            {
                return apellido;
            }

        public void setApellido(String apellido)
            {
                this.apellido = apellido;
            }

        public String getNumeroIdentificacion()
            {
                return numeroIdentificacion;
            }

        public void setNumeroIdentificacion(String numeroIdentificacion)
            {
                this.numeroIdentificacion = numeroIdentificacion;
            }

        public String getCorreo()
            {
                return correo;
            }

        public void setCorreo(String correo)
            {
                this.correo = correo;
            }

        public String getClave()
            {
                return clave;
            }

        public void setClave(String clave)
            {
                this.clave = clave;
            }

        public String getTelefono()
            {
                return telefono;
            }

        public void setTelefono(String telefono)
            {
                this.telefono = telefono;
            }

        public String getDepartamento()
            {
                return departamento;
            }

        public void setDepartamento(String departamento)
            {
                this.departamento = departamento;
            }

        public String getCiudad()
            {
                return ciudad;
            }

        public void setCiudad(String ciudad)
            {
                this.ciudad = ciudad;
            }

        public String getDireccion()
            {
                return direccion;
            }

        public void setDireccion(String direccion)
            {
                this.direccion = direccion;
            }

        public LocalDate getFechaNacimiento()
            {
                return fechaNacimiento;
            }

        public void setFechaNacimiento(LocalDate fechaNacimiento)
            {
                this.fechaNacimiento = fechaNacimiento;
            }

        public String getGenero()
            {
                return genero;
            }

        public void setGenero(String genero)
            {
                this.genero = genero;
            }

        public Boolean getEstado()
            {
                return estado;
            }

        public void setEstado(Boolean estado)
            {
                this.estado = estado;
            }

        public LocalDateTime getFechaRegistro()
            {
                return fechaRegistro;
            }

        public void setFechaRegistro(LocalDateTime fechaRegistro)
            {
                this.fechaRegistro = fechaRegistro;
            }

        public Rol getRol()
            {
                return rol;
            }

        public void setRol(Rol rol)
            {
                this.rol = rol;
            }
    }