-- =====================================================
-- DATA.SQL - Carga automática de datos iniciales
-- Spring Boot ejecuta este archivo después de schema.sql
-- Basado en el esquema actualizado (is_activo en categoria/cliente, is_activo en ticket)
-- =====================================================

-- =====================================================
-- 1. Insertar Categorías (tabla: categoria)
-- =====================================================
INSERT INTO categoria (nombre, descripcion, tiempo_estimado_horas, is_activo)
VALUES ('Hardware', 'Problemas con componentes físicos: computadoras, servidores, impresoras, etc.', 24, TRUE),
       ('Software', 'Problemas con aplicaciones y sistemas operativos', 12, TRUE),
       ('Redes', 'Problemas de conectividad y red', 48, TRUE),
       ('Base de Datos', 'Problemas con bases de datos y consultas', 36, TRUE),
       ('Seguridad', 'Problemas de seguridad y accesos', 72, TRUE),
       ('Correo Electrónico', 'Problemas con correo electrónico', 24, TRUE),
       ('Sitio Web', 'Problemas con páginas web y hosting', 48, TRUE),
       ('Otros', 'Problemas no clasificados', 72, TRUE);

-- =====================================================
-- 2. Insertar Clientes (tabla: cliente)
-- =====================================================
INSERT INTO cliente (identificacion, nombre, apellido, email, telefono, empresa, is_activo)
VALUES ('CLI-001', 'Juan', 'Pérez', 'juan.perez@techsolutions.com', '555-0101', 'Tech Solutions S.A.', TRUE),
       ('CLI-002', 'María', 'González', 'maria.gonzalez@digitalinnovations.com', '555-0102', 'Digital Innovations S.L.',
        TRUE),
       ('CLI-003', 'Carlos', 'Rodríguez', 'carlos.rodriguez@datacenter.com', '555-0103', 'Data Center Corp', TRUE),
       ('CLI-004', 'Ana', 'Martínez', 'ana.martinez@cloudservices.com', '555-0104', 'Cloud Services S.A.', TRUE),
       ('CLI-005', 'Luis', 'Sánchez', 'luis.sanchez@startup.com', '555-0105', 'Startup Innovations', TRUE),
       ('CLI-006', 'Laura', 'Fernández', 'laura.fernandez@empresa1.com', '555-0106', 'Empresa 1 S.A.', TRUE),
       ('CLI-007', 'Diego', 'Ramírez', 'diego.ramirez@negocio.com', '555-0107', 'Negocio Digital S.L.', TRUE),
       ('CLI-008', 'Sofía', 'Torres', 'sofia.torres@tecnologia.com', '555-0108', 'Tecnología Avanzada', TRUE),
       ('CLI-009', 'Javier', 'Flores', 'javier.flores@consultoria.com', '555-0109', 'Consultoría Estratégica', TRUE),
       ('CLI-010', 'Patricia', 'Mendoza', 'patricia.mendoza@logistica.com', '555-0110', 'Logística Integral', TRUE);

-- =====================================================
-- 3. Insertar Técnicos (tabla: tecnico)
-- =====================================================
INSERT INTO tecnico (identificacion, nombre, apellido, telefono, is_activo)
VALUES ('TEC-001', 'Roberto', 'García', '555-1001', TRUE),
       ('TEC-002', 'Elena', 'López', '555-1002', TRUE),
       ('TEC-003', 'Miguel', 'Hernández', '555-1003', TRUE),
       ('TEC-004', 'Carmen', 'Díaz', '555-1004', TRUE),
       ('TEC-005', 'Andrés', 'Moreno', '555-1005', TRUE),
       ('TEC-006', 'Isabel', 'Romero', '555-1006', TRUE),
       ('TEC-007', 'Fernando', 'Vega', '555-1007', TRUE),
       ('TEC-008', 'Natalia', 'Ortiz', '555-1008', TRUE);

-- =====================================================
-- 4. Insertar Tickets (tabla: ticket)
-- Nota: ticket usa "is_activo" (no is_activo)
-- =====================================================

-- Ticket 1: Abierto - Prioridad Alta (Hardware)
INSERT INTO ticket (numero_ticket, titulo, descripcion, estado, prioridad, cliente_id, tecnico_asignado_id,
                    categoria_id, fecha_creacion, is_activo)
VALUES ('TKT-2024001', 'Computadora no enciende', 'La PC no responde al botón de encendido después de un apagón',
        'ABIERTO', 'ALTA', 1, NULL, 1, DATE_SUB(NOW(), INTERVAL 2 DAY), TRUE);

-- Ticket 2: En Proceso - Prioridad Crítica (Base de Datos)
INSERT INTO ticket (numero_ticket, titulo, descripcion, estado, prioridad, cliente_id, tecnico_asignado_id,
                    categoria_id, fecha_creacion, is_activo)
VALUES ('TKT-2024002', 'Servidor BD lento', 'Consultas muy lentas en producción afectando el rendimiento', 'EN_PROCESO',
        'CRITICA', 2, 2, 4, DATE_SUB(NOW(), INTERVAL 1 DAY), TRUE);

-- Ticket 3: Resuelto (Hardware)
INSERT INTO ticket (numero_ticket, titulo, descripcion, estado, prioridad, cliente_id, tecnico_asignado_id,
                    categoria_id, fecha_creacion, fecha_cierre, tiempo_resolucion_horas, is_activo)
VALUES ('TKT-2024003', 'Error de impresión', 'No se puede imprimir desde ninguna aplicación', 'RESUELTO', 'MEDIA', 3, 3,
        1, DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY), 48, TRUE);

-- Ticket 4: Cerrado (Software)
INSERT INTO ticket (numero_ticket, titulo, descripcion, estado, prioridad, cliente_id, tecnico_asignado_id,
                    categoria_id, fecha_creacion, fecha_cierre, tiempo_resolucion_horas, is_activo)
VALUES ('TKT-2024004', 'Actualizar sistema', 'Solicitud de actualización de software corporativo', 'CERRADO', 'BAJA', 4,
        4, 2, DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY), 48, TRUE);

-- Ticket 5: En Proceso - Prioridad Alta (Redes)
INSERT INTO ticket (numero_ticket, titulo, descripcion, estado, prioridad, cliente_id, tecnico_asignado_id,
                    categoria_id, fecha_creacion, is_activo)
VALUES ('TKT-2024005', 'VPN no conecta', 'Usuarios remotos no pueden acceder a la red corporativa', 'EN_PROCESO',
        'ALTA', 5, 3, 3, DATE_SUB(NOW(), INTERVAL 3 DAY), TRUE);

-- Ticket 6: Abierto - Prioridad Media (Correo)
INSERT INTO ticket (numero_ticket, titulo, descripcion, estado, prioridad, cliente_id, tecnico_asignado_id,
                    categoria_id, fecha_creacion, is_activo)
VALUES ('TKT-2024006', 'Correos no llegan', 'Los correos salientes no están llegando a destino', 'ABIERTO', 'MEDIA', 6,
        NULL, 6, DATE_SUB(NOW(), INTERVAL 1 DAY), TRUE);

-- Ticket 7: En Proceso - Prioridad Crítica (Seguridad)
INSERT INTO ticket (numero_ticket, titulo, descripcion, estado, prioridad, cliente_id, tecnico_asignado_id,
                    categoria_id, fecha_creacion, is_activo)
VALUES ('TKT-2024007', 'Intento de acceso no autorizado', 'Múltiples intentos fallidos de acceso al sistema',
        'EN_PROCESO', 'CRITICA', 7, 5, 5, DATE_SUB(NOW(), INTERVAL 1 DAY), TRUE);

-- Ticket 8: Cancelado
INSERT INTO ticket (numero_ticket, titulo, descripcion, estado, prioridad, cliente_id, tecnico_asignado_id,
                    categoria_id, fecha_creacion, is_activo)
VALUES ('TKT-2024008', 'Solicitud de hardware', 'Solicitud de nueva computadora (cancelada por presupuesto)',
        'CANCELADO', 'BAJA', 8, NULL, 1, DATE_SUB(NOW(), INTERVAL 15 DAY), FALSE);

-- Ticket 9: Abierto - Prioridad Alta (Sitio Web)
INSERT INTO ticket (numero_ticket, titulo, descripcion, estado, prioridad, cliente_id, tecnico_asignado_id,
                    categoria_id, fecha_creacion, is_activo)
VALUES ('TKT-2024009', 'Sitio web lento', 'El sitio web corporativo carga muy lento', 'ABIERTO', 'ALTA', 9, 6, 7,
        DATE_SUB(NOW(), INTERVAL 2 DAY), TRUE);

-- Ticket 10: En Espera (Redes)
INSERT INTO ticket (numero_ticket, titulo, descripcion, estado, prioridad, cliente_id, tecnico_asignado_id,
                    categoria_id, fecha_creacion, is_activo)
VALUES ('TKT-2024010', 'Problemas de conectividad', 'Pérdida de paquetes en la red local', 'EN_ESPERA', 'MEDIA', 10, 3,
        3, DATE_SUB(NOW(), INTERVAL 4 DAY), TRUE);

-- =====================================================
-- 5. Insertar Comentarios (tabla: comentario)
-- =====================================================

-- Comentarios para Ticket 1
INSERT INTO comentario (contenido, es_interno, ticket_id, cliente_id, fecha_creacion)
VALUES ('Ya revisé los cables y todo está bien conectado', FALSE, 1, 1, DATE_SUB(NOW(), INTERVAL 2 DAY));

INSERT INTO comentario (contenido, es_interno, ticket_id, tecnico_id, fecha_creacion)
VALUES ('Asignado a soporte nivel 2 para revisión', TRUE, 1, 1, DATE_SUB(NOW(), INTERVAL 1 DAY));

-- Comentarios para Ticket 2
INSERT INTO comentario (contenido, es_interno, ticket_id, tecnico_id, fecha_creacion)
VALUES ('Se está analizando el plan de acción', TRUE, 2, 2, DATE_SUB(NOW(), INTERVAL 23 HOUR));

INSERT INTO comentario (contenido, es_interno, ticket_id, tecnico_id, fecha_creacion)
VALUES ('Optimización de consultas en progreso', TRUE, 2, 2, DATE_SUB(NOW(), INTERVAL 12 HOUR));

-- Comentarios para Ticket 3
INSERT INTO comentario (contenido, es_interno, ticket_id, tecnico_id, fecha_creacion)
VALUES ('El problema era el driver desactualizado', FALSE, 3, 3, DATE_SUB(NOW(), INTERVAL 3 DAY));

INSERT INTO comentario (contenido, es_interno, ticket_id, cliente_id, fecha_creacion)
VALUES ('Gracias, ya funciona correctamente', FALSE, 3, 3, DATE_SUB(NOW(), INTERVAL 3 DAY));

-- Comentarios para Ticket 4
INSERT INTO comentario (contenido, es_interno, ticket_id, tecnico_id, fecha_creacion)
VALUES ('Actualización programada para el viernes', FALSE, 4, 4, DATE_SUB(NOW(), INTERVAL 9 DAY));

INSERT INTO comentario (contenido, es_interno, ticket_id, cliente_id, fecha_creacion)
VALUES ('Confirmo la fecha para la actualización', FALSE, 4, 4, DATE_SUB(NOW(), INTERVAL 9 DAY));

-- Comentarios para Ticket 5
INSERT INTO comentario (contenido, es_interno, ticket_id, cliente_id, fecha_creacion)
VALUES ('Los usuarios no pueden conectar desde casa', FALSE, 5, 5, DATE_SUB(NOW(), INTERVAL 3 DAY));

INSERT INTO comentario (contenido, es_interno, ticket_id, tecnico_id, fecha_creacion)
VALUES ('Revisando configuración del VPN', TRUE, 5, 3, DATE_SUB(NOW(), INTERVAL 2 DAY));

-- Comentarios para Ticket 7
INSERT INTO comentario (contenido, es_interno, ticket_id, cliente_id, fecha_creacion)
VALUES ('Se detectaron múltiples intentos de acceso', FALSE, 7, 7, DATE_SUB(NOW(), INTERVAL 1 DAY));

INSERT INTO comentario (contenido, es_interno, ticket_id, tecnico_id, fecha_creacion)
VALUES ('Bloqueando IP sospechosa', TRUE, 7, 5, DATE_SUB(NOW(), INTERVAL 12 HOUR));

-- =====================================================
-- 6. Insertar Historial (tabla: historial_ticket)
-- =====================================================

INSERT INTO historial_ticket (ticket_id, campo_modificado, valor_anterior, valor_nuevo, usuario_modifico,
                              fecha_modificacion)
VALUES (2, 'estado', 'ABIERTO', 'EN_PROCESO', 'elena.lopez@soporte.com', DATE_SUB(NOW(), INTERVAL 23 HOUR));

INSERT INTO historial_ticket (ticket_id, campo_modificado, valor_anterior, valor_nuevo, usuario_modifico,
                              fecha_modificacion)
VALUES (3, 'estado', 'EN_PROCESO', 'RESUELTO', 'miguel.hernandez@soporte.com', DATE_SUB(NOW(), INTERVAL 3 DAY));

INSERT INTO historial_ticket (ticket_id, campo_modificado, valor_anterior, valor_nuevo, usuario_modifico,
                              fecha_modificacion)
VALUES (4, 'tecnico_asignado_id', NULL, '4', 'roberto.garcia@soporte.com', DATE_SUB(NOW(), INTERVAL 9 DAY));

INSERT INTO historial_ticket (ticket_id, campo_modificado, valor_anterior, valor_nuevo, usuario_modifico,
                              fecha_modificacion)
VALUES (5, 'estado', 'ABIERTO', 'EN_PROCESO', 'miguel.hernandez@soporte.com', DATE_SUB(NOW(), INTERVAL 2 DAY));

INSERT INTO historial_ticket (ticket_id, campo_modificado, valor_anterior, valor_nuevo, usuario_modifico,
                              fecha_modificacion)
VALUES (7, 'prioridad', 'ALTA', 'CRITICA', 'fernando.vega@soporte.com', DATE_SUB(NOW(), INTERVAL 12 HOUR));

INSERT INTO historial_ticket (ticket_id, campo_modificado, valor_anterior, valor_nuevo, usuario_modifico,
                              fecha_modificacion)
VALUES (10, 'estado', 'ABIERTO', 'EN_ESPERA', 'carmen.diaz@soporte.com', DATE_SUB(NOW(), INTERVAL 1 DAY));

-- =====================================================
-- 7. Insertar Usuarios (tabla: usuario)
-- =====================================================
INSERT INTO usuario (nombre, clave, rol, is_actived, is_account_non_locked, is_account_non_expired,
                     is_credentials_non_expired, created_at)
VALUES ('admin', '$2a$10$6g2CeyOg2FKniIMhrGmomuL8N90HctwCKmhS8oJ45F3csVaxDQJHC', 'ADMIN', TRUE, TRUE, TRUE, TRUE,
        NOW()),
       ('esteban', '$2a$10$N.$2a$10$6g2CeyOg2FKniIMhrGmomuL8N90HctwCKmhS8oJ45F3csVaxDQJHC', 'SUPERVISOR', TRUE, TRUE,
        TRUE,
        TRUE, NOW());

-- =====================================================
-- 8. Consultas de verificación
-- =====================================================
SELECT '=== DATOS INSERTADOS CORRECTAMENTE ===' AS Mensaje;

SELECT 'Categorías' as Tabla, COUNT(*) as Registros
FROM categoria
UNION
SELECT 'Clientes', COUNT(*)
FROM cliente
UNION
SELECT 'Técnicos', COUNT(*)
FROM tecnico
UNION
SELECT 'Tickets', COUNT(*)
FROM ticket
UNION
SELECT 'Comentarios', COUNT(*)
FROM comentario
UNION
SELECT 'Historial', COUNT(*)
FROM historial_ticket
UNION
SELECT 'Usuarios', COUNT(*)
FROM usuario;

-- Mostrar resumen de tickets por estado
SELECT 'Resumen de Tickets por Estado:' as Info;
SELECT estado, COUNT(*) as Cantidad
FROM ticket
GROUP BY estado;

-- Mostrar resumen de tickets por prioridad
SELECT 'Resumen de Tickets por Prioridad:' as Info;
SELECT prioridad, COUNT(*) as Cantidad
FROM ticket
GROUP BY prioridad;