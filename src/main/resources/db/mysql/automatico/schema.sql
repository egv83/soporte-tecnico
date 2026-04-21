-- =====================================================
-- SCHEMA.SQL - Creación automática de tablas
-- Spring Boot ejecuta este archivo automáticamente al iniciar
-- Configuración: spring.sql.init.mode=always
-- =====================================================

-- Eliminar tablas si existen (para reinicio limpio)
DROP TABLE IF EXISTS historial_ticket;
DROP TABLE IF EXISTS comentario;
DROP TABLE IF EXISTS ticket;
DROP TABLE IF EXISTS tecnico;
DROP TABLE IF EXISTS cliente;
DROP TABLE IF EXISTS categoria;
DROP TABLE IF EXISTS usuario;

-- =====================================================
-- TABLA: categorias
-- =====================================================
CREATE TABLE categoria (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            nombre VARCHAR(100) NOT NULL UNIQUE,
                            descripcion VARCHAR(500),
                            tiempo_estimado_horas INT,
                            activo BOOLEAN DEFAULT TRUE,
                            fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- TABLA: clientes
-- =====================================================
CREATE TABLE cliente (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          identificacion VARCHAR(20) UNIQUE,
                          nombre VARCHAR(100) NOT NULL,
                          apellido VARCHAR(100) NOT NULL,
                          email VARCHAR(150) NOT NULL UNIQUE,
                          telefono VARCHAR(20),
                          empresa VARCHAR(200),
                          activo BOOLEAN DEFAULT TRUE,
                          fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- TABLA: tecnicos
-- =====================================================
CREATE TABLE tecnico (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(100) NOT NULL,
                          apellido VARCHAR(100) NOT NULL,
                          email VARCHAR(150) NOT NULL UNIQUE,
                          telefono VARCHAR(20),
                          rol VARCHAR(50) NOT NULL,
                          especialidad VARCHAR(200),
                          activo BOOLEAN DEFAULT TRUE,
                          fecha_contratacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- TABLA: tickets
-- =====================================================
CREATE TABLE ticket (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         numero_ticket VARCHAR(50) NOT NULL UNIQUE,
                         titulo VARCHAR(200) NOT NULL,
                         descripcion TEXT,
                         estado VARCHAR(50) NOT NULL DEFAULT 'ABIERTO',
                         prioridad VARCHAR(20) NOT NULL DEFAULT 'MEDIA',
                         cliente_id BIGINT NOT NULL,
                         tecnico_asignado_id BIGINT,
                         categoria_id BIGINT NOT NULL,
                         fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         fecha_cierre TIMESTAMP NULL,
                         tiempo_resolucion_horas INT,
                         activo BOOLEAN DEFAULT TRUE,
                         FOREIGN KEY (cliente_id) REFERENCES cliente(id),
                         FOREIGN KEY (tecnico_asignado_id) REFERENCES tecnico(id),
                         FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

-- =====================================================
-- TABLA: comentarios
-- =====================================================
CREATE TABLE comentario (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             contenido TEXT NOT NULL,
                             es_interno BOOLEAN DEFAULT FALSE,
                             ticket_id BIGINT NOT NULL,
                             tecnico_id BIGINT,
                             cliente_id BIGINT,
                             fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             FOREIGN KEY (ticket_id) REFERENCES ticket(id) ON DELETE CASCADE,
                             FOREIGN KEY (tecnico_id) REFERENCES tecnico(id),
                             FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

-- =====================================================
-- TABLA: historial_tickets
-- =====================================================
CREATE TABLE historial_ticket (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   ticket_id BIGINT NOT NULL,
                                   campo_modificado VARCHAR(100) NOT NULL,
                                   valor_anterior TEXT,
                                   valor_nuevo TEXT,
                                   usuario_modifico VARCHAR(200),
                                   fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   FOREIGN KEY (ticket_id) REFERENCES ticket(id) ON DELETE CASCADE
);

-- =====================================================
-- TABLA: usuario
-- =====================================================
CREATE TABLE usuario (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  nombre VARCHAR(20) NOT NULL,
                                  clave VARCHAR(255) NOT NULL,
                                  rol VARCHAR(20),
                                  is_actived BOOLEAN default true,
                                  is_account_non_locked BOOLEAN default true,
                                  is_account_non_expired BOOLEAN default true,
                                  is_credentials_non_expired BOOLEAN default true,
                                  created_at DATETIME
);

-- Crear índices para rendimiento
CREATE INDEX idx_tickets_estado ON ticket(estado);
CREATE INDEX idx_tickets_cliente ON ticket(cliente_id);
CREATE INDEX idx_tickets_tecnico ON ticket(tecnico_asignado_id);
CREATE INDEX idx_comentarios_ticket ON comentario(ticket_id);