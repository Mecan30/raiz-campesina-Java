
package com.raizcampesina.model;

import jakarta.persistence.*;

// Catálogo maestro de seguridad del sistema.
// Determina los perfiles de acceso (ADMINISTRADOR, PROVEEDOR, CONSUMIDOR, TRANSPORTADOR).
@Entity
@Table(name = "roles")
public class Rol
    {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_rol")
        private Integer idRol;

        @Column(name = "nombre_rol")
        private String nombreRol;

        public Rol()
            {}

        public Integer getIdRol()
            {
                return idRol;
            }

        public void setIdRol(Integer idRol)
            {
                this.idRol = idRol;
            }

        public String getNombreRol()
            {
                return nombreRol;
            }

        public void setNombreRol(String nombreRol)
            {
                this.nombreRol = nombreRol;
            }
    }