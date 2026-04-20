-- =====================================================
-- MANUAL - CREACIÓN DE BASE DE DATOS
-- =====================================================
-- Título: Script de creación de base de datos (MySQL/PostgreSQL)
-- Autor: Sistema de Soporte Técnico
-- Versión: 1.0
-- Fecha: 2024
-- Uso: mysql -u root -p < 01_create_database.sql
-- =====================================================

-- =====================================================
-- SECCIÓN 1: Crear base de datos (Descomentar según BD)
-- =====================================================

-- Para MySQL/MariaDB:
-- CREATE DATABASE IF NOT EXISTS soporte_tecnico
-- CHARACTER SET utf8mb4
-- COLLATE utf8mb4_unicode_ci;

-- USE soporte_tecnico;

-- Para PostgreSQL:
-- CREATE DATABASE soporte_tecnico
-- WITH ENCODING 'UTF8'
-- LC_COLLATE='es_ES.UTF-8'
-- LC_CTYPE='es_ES.UTF-8';

-- Para H2 (embebida):
-- CREATE DATABASE IF NOT EXISTS soporte_tecnico;

-- =====================================================
-- SECCIÓN 2: Crear tablas con documentación completa
-- =====================================================

-- 2.1 Tabla de categorías
DROP TABLE IF EXISTS categoria CASCADE;
CREATE TABLE categoria (
                            id SERIAL PRIMARY KEY COMMENT 'Identificador único',
                            nombre VARCHAR(100) NOT NULL UNIQUE COMMENT 'Nombre de la categoría',
                            descripcion TEXT COMMENT 'Descripción detallada',
                            tiempo_estimado_horas INT COMMENT 'Tiempo estimado de resolución',
                            activo BOOLEAN DEFAULT TRUE COMMENT 'Estado de la categoría',
                            creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de creación',
                            actualizado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Última actualización'
) COMMENT='Catálogo de categorías de tickets';

-- 2.2 Tabla de clientes
DROP TABLE IF EXISTS cliente CASCADE;
CREATE TABLE cliente (
                          id SERIAL PRIMARY KEY COMMENT 'Identificador único',
                          nombre VARCHAR(100) NOT NULL COMMENT 'Nombre del cliente',
                          apellido VARCHAR(100) NOT NULL COMMENT 'Apellido del cliente',
                          email VARCHAR(150) NOT NULL UNIQUE COMMENT 'Correo electrónico',
                          telefono VARCHAR(20) COMMENT 'Teléfono de contacto',
                          empresa VARCHAR(200) COMMENT 'Empresa del cliente',
                          activo BOOLEAN DEFAULT TRUE COMMENT 'Cliente activo',
                          creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de registro'
) COMMENT='Información de clientes';

-- 2.3 Tabla de técnicos
DROP TABLE IF EXISTS tecnico CASCADE;
CREATE TABLE tecnico (
                          id SERIAL PRIMARY KEY COMMENT 'Identificador único',
                          nombre VARCHAR(100) NOT NULL COMMENT 'Nombre del técnico',
                          apellido VARCHAR(100) NOT NULL COMMENT 'Apellido del técnico',
                          email VARCHAR(150) NOT NULL UNIQUE COMMENT 'Correo electrónico',
                          telefono VARCHAR(20) COMMENT 'Teléfono de contacto',
                          rol VARCHAR(50) NOT NULL COMMENT 'Rol: SUPERVISOR, NIVEL1, NIVEL2, NIVEL3, ADMIN',
                          especialidad VARCHAR(200) COMMENT 'Área de especialización',
                          activo BOOLEAN DEFAULT TRUE COMMENT 'Técnico activo',
                          contratado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de contratación'
) COMMENT='Información de técnicos de soporte';

-- 2.4 Tabla de tickets (principal)
DROP TABLE IF EXISTS ticket CASCADE;
CREATE TABLE ticket (
                         id SERIAL PRIMARY KEY COMMENT 'Identificador único',
                         numero_ticket VARCHAR(50) NOT NULL UNIQUE COMMENT 'Número único del ticket',
                         titulo VARCHAR(200) NOT NULL COMMENT 'Título del problema',
                         descripcion TEXT COMMENT 'Descripción detallada',
                         estado VARCHAR(50) NOT NULL DEFAULT 'ABIERTO' COMMENT 'Estado actual',
                         prioridad VARCHAR(20) NOT NULL DEFAULT 'MEDIA' COMMENT 'Prioridad: BAJA, MEDIA, ALTA, CRITICA',
                         cliente_id BIGINT NOT NULL COMMENT 'ID del cliente',
                         tecnico_asignado_id BIGINT COMMENT 'ID del técnico asignado',
                         categoria_id BIGINT NOT NULL COMMENT 'ID de la categoría',
                         fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de creación',
                         fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Última actualización',
                         fecha_cierre TIMESTAMP NULL COMMENT 'Fecha de cierre',
                         tiempo_resolucion_horas INT COMMENT 'Horas totales para resolver',
                         activo BOOLEAN DEFAULT TRUE COMMENT 'Ticket activo',

    -- Llaves foráneas
                         FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE RESTRICT,
                         FOREIGN KEY (tecnico_asignado_id) REFERENCES tecnico(id) ON DELETE SET NULL,
                         FOREIGN KEY (categoria_id) REFERENCES categoria(id) ON DELETE RESTRICT,

    -- Índices para rendimiento
                         INDEX idx_tickets_numero (numero_ticket),
                         INDEX idx_tickets_estado (estado),
                         INDEX idx_tickets_prioridad (prioridad),
                         INDEX idx_tickets_cliente (cliente_id),
                         INDEX idx_tickets_tecnico (tecnico_asignado_id),
                         INDEX idx_tickets_fecha (fecha_creacion)
) COMMENT='Tabla principal de tickets de soporte';

-- 2.5 Tabla historial tickets
DROP TABLE IF EXISTS historial_ticket CASCADE;
CREATE TABLE historial_ticket (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  ticket_id BIGINT NOT NULL,
                                  campo_modificado VARCHAR(100) NOT NULL,
                                  valor_anterior TEXT,
                                  valor_nuevo TEXT,
                                  usuario_modifico VARCHAR(200),
                                  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  FOREIGN KEY (ticket_id) REFERENCES ticket(id) ON DELETE CASCADE
)COMMENT='Tabla historil de tickets de soporte';


-- 2.6 Tabla de usuarios
DROP TABLE IF EXISTS usuario CASCADE;
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
)COMMENT='Tabla de usuarios';

-- 2.7 Tabla de comentarios
DROP TABLE IF EXISTS comentario CASCADE;
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
)COMMENT='Tabla de comentarios'

-- Mostrar resultado
SELECT 'Base de datos creada exitosamente' AS Mensaje;
SELECT COUNT(*) AS Tablas_Creadas FROM information_schema.tables WHERE table_schema = DATABASE();