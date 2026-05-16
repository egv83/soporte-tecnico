-- =====================================================
-- MANUAL - CREACIÓN DE BASE DE DATOS
-- =====================================================
-- Título: Script de creación de base de datos (MySQL)
-- Autor: Sistema de Soporte Técnico
-- Versión: 1.0
-- Fecha: 2024
-- Uso: mysql -u root -p < 01_create_database.sql
-- =====================================================

-- =====================================================
-- SECCIÓN 1: Crear base de datos
-- =====================================================

-- Crear base de datos para MySQL/MariaDB
CREATE DATABASE IF NOT EXISTS db_soporte_tecnico_v1
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE db_soporte_tecnico_v1;

-- =====================================================
-- SECCIÓN 2: Eliminar tablas si existen (orden inverso por FK)
-- =====================================================
DROP TABLE IF EXISTS historial_ticket;
DROP TABLE IF EXISTS comentario;
DROP TABLE IF EXISTS ticket;
DROP TABLE IF EXISTS tecnico;
DROP TABLE IF EXISTS cliente;
DROP TABLE IF EXISTS categoria;
DROP TABLE IF EXISTS usuario;

-- =====================================================
-- SECCIÓN 3: Crear tablas con documentación completa
-- =====================================================

-- 3.1 Tabla de categorías
CREATE TABLE categoria (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Identificador único',
                           nombre VARCHAR(100) NOT NULL UNIQUE COMMENT 'Nombre de la categoría',
                           descripcion VARCHAR(500) COMMENT 'Descripción detallada',
                           tiempo_estimado_horas INT COMMENT 'Tiempo estimado de resolución en horas',
                           is_activo BOOLEAN DEFAULT TRUE COMMENT 'Estado de la categoría (activo/inactivo)',
                           fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de creación del registro'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Catálogo de categorías de tickets';

-- 3.2 Tabla de clientes
CREATE TABLE cliente (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Identificador único',
                         identificacion VARCHAR(20) UNIQUE COMMENT 'Número de identificación del cliente',
                         nombre VARCHAR(100) NOT NULL COMMENT 'Nombre del cliente',
                         apellido VARCHAR(100) NOT NULL COMMENT 'Apellido del cliente',
                         email VARCHAR(150) NOT NULL UNIQUE COMMENT 'Correo electrónico',
                         telefono VARCHAR(20) COMMENT 'Teléfono de contacto',
                         empresa VARCHAR(200) COMMENT 'Empresa del cliente',
                         is_activo BOOLEAN DEFAULT TRUE COMMENT 'Cliente activo/inactivo',
                         fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de registro del cliente'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Información de clientes';

-- 3.3 Tabla de técnicos
CREATE TABLE tecnico (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Identificador único',
                         identificacion VARCHAR(20) UNIQUE COMMENT 'Número de identificación del técnico',
                         nombre VARCHAR(100) NOT NULL COMMENT 'Nombre del técnico',
                         apellido VARCHAR(100) NOT NULL COMMENT 'Apellido del técnico',
                         telefono VARCHAR(20) COMMENT 'Teléfono de contacto',
                         is_activo BOOLEAN DEFAULT TRUE COMMENT 'Técnico activo/inactivo',
                         fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de registro del técnico'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Información de técnicos de soporte';

-- 3.4 Tabla de tickets (principal)
CREATE TABLE ticket (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Identificador único',
                        numero_ticket VARCHAR(50) NOT NULL UNIQUE COMMENT 'Número único del ticket',
                        titulo VARCHAR(200) NOT NULL COMMENT 'Título del problema',
                        descripcion TEXT COMMENT 'Descripción detallada del problema',
                        estado VARCHAR(50) NOT NULL DEFAULT 'ABIERTO' COMMENT 'Estado actual: ABIERTO, EN_PROCESO, EN_ESPERA, RESUELTO, CERRADO, CANCELADO',
                        prioridad VARCHAR(20) NOT NULL DEFAULT 'MEDIA' COMMENT 'Prioridad: BAJA, MEDIA, ALTA, CRITICA',
                        cliente_id BIGINT NOT NULL COMMENT 'ID del cliente que reporta',
                        tecnico_asignado_id BIGINT COMMENT 'ID del técnico asignado',
                        categoria_id BIGINT NOT NULL COMMENT 'ID de la categoría',
                        fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de creación del ticket',
                        fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Última fecha de actualización',
                        fecha_cierre TIMESTAMP NULL COMMENT 'Fecha de cierre del ticket',
                        tiempo_resolucion_horas INT COMMENT 'Horas totales para resolver el ticket',

    -- Llaves foráneas
                        FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE RESTRICT,
                        FOREIGN KEY (tecnico_asignado_id) REFERENCES tecnico(id) ON DELETE SET NULL,
                        FOREIGN KEY (categoria_id) REFERENCES categoria(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla principal de tickets de soporte';

-- 3.5 Tabla de comentarios
CREATE TABLE comentario (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Identificador único',
                            contenido TEXT NOT NULL COMMENT 'Contenido del comentario',
                            es_interno BOOLEAN DEFAULT FALSE COMMENT 'Indica si el comentario es interno (solo visible para técnicos)',
                            ticket_id BIGINT NOT NULL COMMENT 'ID del ticket asociado',
                            tecnico_id BIGINT COMMENT 'ID del técnico que comenta',
                            cliente_id BIGINT COMMENT 'ID del cliente que comenta',
                            fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de creación del comentario',

    -- Llaves foráneas
                            FOREIGN KEY (ticket_id) REFERENCES ticket(id) ON DELETE CASCADE,
                            FOREIGN KEY (tecnico_id) REFERENCES tecnico(id) ON DELETE SET NULL,
                            FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de comentarios y seguimiento de tickets';

-- 3.6 Tabla de historial de tickets
CREATE TABLE historial_ticket (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Identificador único',
                                  ticket_id BIGINT NOT NULL COMMENT 'ID del ticket modificado',
                                  campo_modificado VARCHAR(100) NOT NULL COMMENT 'Nombre del campo modificado',
                                  valor_anterior TEXT COMMENT 'Valor anterior del campo',
                                  valor_nuevo TEXT COMMENT 'Valor nuevo del campo',
                                  usuario_modifico VARCHAR(200) COMMENT 'Usuario que realizó la modificación',
                                  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de la modificación',

    -- Llaves foráneas
                                  FOREIGN KEY (ticket_id) REFERENCES ticket(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de auditoría de cambios en tickets';

-- 3.7 Tabla de usuarios
CREATE TABLE usuario (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Identificador único',
                         nombre VARCHAR(20) NOT NULL COMMENT 'Nombre de usuario',
                         clave VARCHAR(255) NOT NULL COMMENT 'Contraseña encriptada',
                         rol VARCHAR(20) COMMENT 'Rol del usuario: ADMIN, SUPERVISOR, TECNICO, CLIENTE',
                         is_actived BOOLEAN DEFAULT TRUE COMMENT 'Usuario activo/inactivo',
                         is_account_non_locked BOOLEAN DEFAULT TRUE COMMENT 'Cuenta no bloqueada',
                         is_account_non_expired BOOLEAN DEFAULT TRUE COMMENT 'Cuenta no expirada',
                         is_credentials_non_expired BOOLEAN DEFAULT TRUE COMMENT 'Credenciales no expiradas',
                         created_at DATETIME COMMENT 'Fecha de creación del usuario'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de usuarios del sistema';

-- =====================================================
-- SECCIÓN 5: Verificación
-- =====================================================

-- Mostrar resultado
SELECT '=== BASE DE DATOS CREADA EXITOSAMENTE ===' AS Mensaje;

-- Mostrar todas las tablas creadas
SELECT 'Tablas creadas:' AS Info;
SHOW TABLES;

-- Contar tablas creadas
SELECT COUNT(*) AS Total_Tablas_Creadas
FROM information_schema.tables
WHERE table_schema = 'db_soporte_tecnico_v1';

-- Mostrar estructura de tablas
SELECT 'Estructura de tablas:' AS Info;
SELECT TABLE_NAME, ENGINE, TABLE_ROWS, CREATE_TIME
FROM information_schema.tables
WHERE table_schema = 'db_soporte_tecnico_v1'
ORDER BY TABLE_NAME;