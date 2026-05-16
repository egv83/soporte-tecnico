-- =====================================================
-- SCHEMA.SQL - Creación automática de tablas
-- Spring Boot ejecuta este archivo automáticamente al iniciar
-- Configuración: spring.sql.init.mode=always
-- =====================================================

-- 1. Eliminar vistas primero (dependen de tablas)
DROP VIEW IF EXISTS vw_tickets_detalle;

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
                            is_activo BOOLEAN DEFAULT TRUE,
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
                          is_activo BOOLEAN DEFAULT TRUE,
                          fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- TABLA: tecnicos
-- =====================================================
CREATE TABLE tecnico (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          identificacion VARCHAR(20) UNIQUE,
                          nombre VARCHAR(100) NOT NULL,
                          apellido VARCHAR(100) NOT NULL,
                          telefono VARCHAR(20),
                          is_activo BOOLEAN DEFAULT TRUE,
                          fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
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

-- =====================================================
-- VISTA: Tickets detallados
-- =====================================================
-- Detalle de tickets con información desnormalizada
DROP VIEW IF EXISTS vw_tickets_detalle;
CREATE VIEW vw_tickets_detalle AS
SELECT
    t.id,
    t.numero_ticket,
    t.titulo,
    SUBSTRING(t.descripcion, 1, 100) AS descripcion_resumida,
    t.estado,
    t.prioridad,
    -- Datos del cliente
    CONCAT(c.nombre, ' ', c.apellido) AS cliente_nombre,
    c.email AS cliente_email,
    c.telefono AS cliente_telefono,
    c.empresa AS cliente_empresa,
    -- Datos del técnico
    CONCAT(tec.nombre, ' ', tec.apellido) AS tecnico_nombre,
    tec.telefono AS tecnico_telefono,
    -- Datos de la categoría
    cat.nombre AS categoria_nombre,
    cat.tiempo_estimado_horas AS categoria_tiempo_estimado,
    -- Fechas formateadas
    DATE_FORMAT(t.fecha_creacion, '%Y-%m-%d %H:%i') AS fecha_creacion,
    DATE_FORMAT(t.fecha_actualizacion, '%Y-%m-%d %H:%i') AS fecha_actualizacion,
    DATE_FORMAT(t.fecha_cierre, '%Y-%m-%d %H:%i') AS fecha_cierre,
    -- Tiempos calculados
    t.tiempo_resolucion_horas,
    TIMESTAMPDIFF(HOUR, t.fecha_creacion, NOW()) AS horas_transcurridas,
    -- Descripción legible del estado
    CASE t.estado
        WHEN 'ABIERTO' THEN 'Abierto - Pendiente de atencion'
        WHEN 'EN_PROCESO' THEN 'En proceso - Siendo atendido'
        WHEN 'EN_ESPERA' THEN 'En espera - Requiere informacion'
        WHEN 'RESUELTO' THEN 'Resuelto - Pendiente confirmacion'
        WHEN 'CERRADO' THEN 'Cerrado - Finalizado'
        WHEN 'CANCELADO' THEN 'Cancelado'
        END AS estado_descripcion,
    -- Indicador visual de prioridad
    CASE t.prioridad
        WHEN 'CRITICA' THEN 'CRITICA - Atencion inmediata'
        WHEN 'ALTA' THEN 'ALTA - Prioridad maxima'
        WHEN 'MEDIA' THEN 'MEDIA - Prioridad normal'
        WHEN 'BAJA' THEN 'BAJA - Sin urgencia'
        END AS prioridad_visual,
    -- Estado del ticket basado en su estado actual
    CASE
        WHEN t.estado IN ('ABIERTO', 'EN_PROCESO', 'EN_ESPERA') THEN 'EN CURSO'
        WHEN t.estado IN ('RESUELTO', 'CERRADO') THEN 'FINALIZADO'
        WHEN t.estado = 'CANCELADO' THEN 'CANCELADO'
        ELSE t.estado
        END AS estado_ticket
FROM ticket t
         LEFT JOIN cliente c ON t.cliente_id = c.id AND c.is_activo = 1
         LEFT JOIN tecnico tec ON t.tecnico_asignado_id = tec.id AND tec.is_activo = 1
         LEFT JOIN categoria cat ON t.categoria_id = cat.id AND cat.is_activo = 1;