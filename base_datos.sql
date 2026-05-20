
CREATE DATABASE raiz_campesina;

USE raiz_campesina;

-- Tablas Principales

-- Tabla De Roles

CREATE TABLE roles
	(
		id_rol INT AUTO_INCREMENT PRIMARY KEY,
		nombre_rol VARCHAR(50) NOT NULL UNIQUE
	);

INSERT INTO roles (nombre_rol)
		VALUES
		('ADMINISTRADOR'),
		('PROVEEDOR'),
		('CONSUMIDOR'),
		('TRANSPORTADOR');

-- Tabla De Usuarios

CREATE TABLE usuarios
	(
		id_usuario INT AUTO_INCREMENT PRIMARY KEY,
		nombre VARCHAR(100) NOT NULL,
		apellido VARCHAR(100) NOT NULL,
		numero_identificacion VARCHAR(30) NOT NULL UNIQUE,
		correo VARCHAR(150) NOT NULL UNIQUE,
		clave VARCHAR(255) NOT NULL,
		telefono VARCHAR(30),
		departamento VARCHAR(100),
		ciudad VARCHAR(100),
		direccion VARCHAR(200),
		fecha_nacimiento DATE,
		genero VARCHAR(20),

		estado BOOLEAN DEFAULT TRUE,
		fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

		id_rol INT NOT NULL,

		FOREIGN KEY (id_rol)
			REFERENCES roles(id_rol)
	);

-- Tabla De Categorías

CREATE TABLE categorias
	(
		id_categoria INT AUTO_INCREMENT PRIMARY KEY,
		nombre_categoria VARCHAR(100) NOT NULL,
		descripcion TEXT
	);

-- Tabla De Productos

CREATE TABLE productos
	(
		id_producto INT AUTO_INCREMENT PRIMARY KEY,
		nombre VARCHAR(150) NOT NULL,
		descripcion TEXT,
		precio DECIMAL(10,2) NOT NULL,
		stock INT NOT NULL,
		disponible BOOLEAN DEFAULT TRUE,

		id_proveedor INT NOT NULL,
		id_categoria INT,

		fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

		FOREIGN KEY (id_proveedor)
			REFERENCES usuarios(id_usuario),

		FOREIGN KEY (id_categoria)
			REFERENCES categorias(id_categoria)
	);

-- Tabla de Carrito

CREATE TABLE carrito
	(
		id_carrito INT AUTO_INCREMENT PRIMARY KEY,
		id_consumidor INT NOT NULL,
		fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

		FOREIGN KEY (id_consumidor)
			REFERENCES usuarios(id_usuario)
	);

-- Tabla De Pedidos

CREATE TABLE pedidos
	(
		id_pedido INT AUTO_INCREMENT PRIMARY KEY,

		id_consumidor INT NOT NULL,
		id_transportador INT NULL,

		fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
		estado VARCHAR(50) DEFAULT 'PENDIENTE',
		total DECIMAL(10,2) DEFAULT 0,

		direccion_entrega VARCHAR(255),

		FOREIGN KEY (id_consumidor)
			REFERENCES usuarios(id_usuario),

		FOREIGN KEY (id_transportador)
			REFERENCES usuarios(id_usuario)
	);

-- Detalle Del Pedido

CREATE TABLE detalle_pedido
	(
		id_detalle INT AUTO_INCREMENT PRIMARY KEY,

		id_pedido INT NOT NULL,
		id_producto INT NOT NULL,

		cantidad INT NOT NULL,
		precio_unitario DECIMAL(10,2) NOT NULL,
		subtotal DECIMAL(10,2) NOT NULL,

		FOREIGN KEY (id_pedido)
			REFERENCES pedidos(id_pedido),

		FOREIGN KEY (id_producto)
			REFERENCES productos(id_producto)
	);
    
-- Tablas Complementarias

-- Tabla De Mensajes

CREATE TABLE mensajes
	(
		id_mensaje INT AUTO_INCREMENT PRIMARY KEY,

		id_remitente INT NOT NULL,
		id_destinatario INT NOT NULL,
		id_pedido INT NULL,

		mensaje TEXT NOT NULL,
		fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

		FOREIGN KEY (id_remitente)
			REFERENCES usuarios(id_usuario),

		FOREIGN KEY (id_destinatario)
			REFERENCES usuarios(id_usuario),

		FOREIGN KEY (id_pedido)
			REFERENCES pedidos(id_pedido)
	);

-- Tabla De Notificaciones

CREATE TABLE notificaciones
	(
		id_notificacion INT AUTO_INCREMENT PRIMARY KEY,

		id_usuario INT NOT NULL,
		titulo VARCHAR(150),
		descripcion TEXT,
		leida BOOLEAN DEFAULT FALSE,

		fecha_notificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

		FOREIGN KEY (id_usuario)
			REFERENCES usuarios(id_usuario)
	);

-- Tabla de Valoraciones

CREATE TABLE valoraciones
	(
		id_valoracion INT AUTO_INCREMENT PRIMARY KEY,

		id_consumidor INT NOT NULL,
		id_producto INT NULL,
		id_proveedor INT NULL,
		id_transportador INT NULL,
		id_pedido INT NULL,

		puntuacion INT NOT NULL,
		comentario TEXT,

		fecha_valoracion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

		FOREIGN KEY (id_consumidor)
			REFERENCES usuarios(id_usuario),

		FOREIGN KEY (id_producto)
			REFERENCES productos(id_producto),

		FOREIGN KEY (id_proveedor)
			REFERENCES usuarios(id_usuario),

		FOREIGN KEY (id_transportador)
			REFERENCES usuarios(id_usuario),

		FOREIGN KEY (id_pedido)
			REFERENCES pedidos(id_pedido)
	);

-- Tabla Del Historial

CREATE TABLE historial
	(
		id_historial INT AUTO_INCREMENT PRIMARY KEY,

		id_usuario INT NOT NULL,
		accion VARCHAR(255),
		descripcion TEXT,

		fecha_historial TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

		FOREIGN KEY (id_usuario)
			REFERENCES usuarios(id_usuario)
	);

-- Tabla De Reportes

CREATE TABLE reportes
	(
		id_reporte INT AUTO_INCREMENT PRIMARY KEY,

		id_administrador INT NOT NULL,
		tipo_reporte VARCHAR(100),
		descripcion TEXT,

		fecha_reporte TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

		FOREIGN KEY (id_administrador)
			REFERENCES usuarios(id_usuario)
	);

-- Tabla De Alertas

CREATE TABLE alertas
	(
		id_alerta INT AUTO_INCREMENT PRIMARY KEY,

		id_administrador INT NOT NULL,
		tipo_alerta VARCHAR(100),
		descripcion TEXT,
		estado VARCHAR(50) DEFAULT 'PENDIENTE',

		fecha_alerta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

		FOREIGN KEY (id_administrador)
			REFERENCES usuarios(id_usuario)
	);

-- Tabla De Logistica Y Entrega

CREATE TABLE logistica_entrega
	(
		id_logistica INT AUTO_INCREMENT PRIMARY KEY,

		id_pedido INT NOT NULL,
		id_transportador INT NOT NULL,

		ruta TEXT,
		estado_entrega VARCHAR(100),
		fecha_salida TIMESTAMP NULL,
		fecha_entrega TIMESTAMP NULL,

		FOREIGN KEY (id_pedido)
			REFERENCES pedidos(id_pedido),

		FOREIGN KEY (id_transportador)
			REFERENCES usuarios(id_usuario)
	);
    
-- Tabla De Favoritos
    
CREATE TABLE favoritos
	(
		id_favorito INT AUTO_INCREMENT PRIMARY KEY,

		id_usuario INT NOT NULL,
		id_producto INT NOT NULL,

		fecha_favorito TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

		CONSTRAINT fk_favorito_usuario
			FOREIGN KEY (id_usuario)
			REFERENCES usuarios(id_usuario),

		CONSTRAINT fk_favorito_producto
			FOREIGN KEY (id_producto)
			REFERENCES productos(id_producto)
	);

SELECT * FROM usuarios;

SELECT * FROM productos;

DESCRIBE usuarios;

ALTER TABLE productos ADD COLUMN imagen VARCHAR(255) DEFAULT 'default-producto.png';

SELECT id_producto, nombre, imagen FROM productos;

SELECT * FROM roles;

INSERT INTO usuarios (nombre, correo, clave, id_rol) 
VALUES ('Administrador', 'admin@raizcampesina.com', '1111', 1);

ALTER TABLE notificaciones 
ADD COLUMN visible_usuario BOOLEAN NOT NULL DEFAULT TRUE;

SELECT * FROM notificaciones; 

SELECT * FROM productos;

SELECT * FROM pedidos;

SELECT * FROM Logistica_Entrega;

SELECT * FROM historial;

SELECT * FROM valoraciones;

SELECT * FROM mensajes;

SELECT * FROM favoritos;