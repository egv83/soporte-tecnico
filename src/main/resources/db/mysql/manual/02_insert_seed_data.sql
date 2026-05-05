-- =====================================================
-- MANUAL - CARGA DE DATOS INICIALES
-- =====================================================
-- Título: Script de inserción de datos semilla
-- Propósito: Poblar la base con datos iniciales necesarios
-- Uso: mysql -u root -p db_soporte_tecnico_v1 < 02_insert_data.sql
-- =====================================================

USE db_soporte_tecnico_v1;

-- =====================================================
-- 1. CATEGORÍAS (Datos base del sistema)
-- =====================================================
INSERT INTO categoria (nombre, descripcion, tiempo_estimado_horas, is_activo) VALUES
-- Categorías de hardware
('Hardware - PCs', 'Problemas con computadoras de escritorio, laptops, componentes internos', 24, TRUE),
('Hardware - Periféricos', 'Problemas con impresoras, scanners, monitores, teclados, mouse', 12, TRUE),
('Hardware - Servidores', 'Problemas con servidores físicos, blades, racks', 48, TRUE),

-- Categorías de software
('Software - SO', 'Problemas con Windows, Linux, macOS', 16, TRUE),
('Software - Aplicaciones', 'Problemas con software ofimático, ERP, CRM', 24, TRUE),
('Software - Desarrollo', 'Problemas con IDEs, compiladores, control de versiones', 36, TRUE),

-- Categorías de redes
('Redes - LAN', 'Problemas de conectividad local, switches, routers internos', 48, TRUE),
('Redes - WAN/VPN', 'Problemas de conectividad remota, VPN, MPLS', 72, TRUE),
('Redes - WiFi', 'Problemas de conectividad inalámbrica', 24, TRUE),

-- Categorías de bases de datos
('BD - MySQL', 'Problemas específicos de MySQL/MariaDB', 36, TRUE),
('BD - PostgreSQL', 'Problemas específicos de PostgreSQL', 36, TRUE),
('BD - SQL Server', 'Problemas específicos de SQL Server', 48, TRUE),

-- Otras categorías
('Seguridad', 'Problemas de seguridad, permisos, accesos', 72, TRUE),
('Backup', 'Problemas con copias de seguridad y restauración', 48, TRUE),
('Correo', 'Problemas con servidores y clientes de correo', 24, TRUE),
('Sitios Web', 'Problemas con páginas web, hosting, dominios', 48, TRUE);

-- =====================================================
-- 2. CLIENTES (Usuarios del sistema)
-- =====================================================
INSERT INTO cliente (identificacion, nombre, apellido, email, telefono, empresa, is_activo) VALUES
-- Empresa Tech Solutions
('CLI-001', 'Juan Carlos', 'Pérez Rodríguez', 'jc.perez@techsolutions.com', '+34 911 234 567', 'Tech Solutions S.A.', TRUE),
('CLI-002', 'María Dolores', 'González López', 'md.gonzalez@techsolutions.com', '+34 911 234 568', 'Tech Solutions S.A.', TRUE),
('CLI-003', 'Luis Miguel', 'Sánchez García', 'lm.sanchez@techsolutions.com', '+34 911 234 569', 'Tech Solutions S.A.', TRUE),

-- Empresa Digital Innovations
('CLI-004', 'Ana Belén', 'Martínez Ruiz', 'ab.martinez@digitalinnovations.com', '+34 922 345 678', 'Digital Innovations S.L.', TRUE),
('CLI-005', 'Carlos Javier', 'Rodríguez Fernández', 'cj.rodriguez@digitalinnovations.com', '+34 922 345 679', 'Digital Innovations S.L.', TRUE),

-- Empresa Data Center
('CLI-006', 'Elena María', 'López Díaz', 'em.lopez@datacenter.com', '+34 933 456 789', 'Data Center Corp', TRUE),
('CLI-007', 'David', 'Fernández Gómez', 'd.fernandez@datacenter.com', '+34 933 456 790', 'Data Center Corp', TRUE),

-- Otras empresas
('CLI-008', 'Sofía', 'Torres Mendoza', 'sofia.torres@cloudservices.com', '+34 944 567 890', 'Cloud Services S.A.', TRUE),
('CLI-009', 'Javier', 'Flores Ramírez', 'j.flores@startup.com', '+34 955 678 901', 'Startup Innovations', TRUE),
('CLI-010', 'Patricia', 'Mendoza Castro', 'p.mendoza@negocio.com', '+34 966 789 012', 'Negocio Digital S.L.', TRUE);

-- =====================================================
-- 3. TÉCNICOS (Personal de soporte)
-- Nota: La tabla tecnico NO tiene campos: email, rol, especialidad
-- =====================================================
INSERT INTO tecnico (identificacion, nombre, apellido, telefono, is_activo) VALUES
-- Nivel 1 (Soporte inicial)
('TEC-001', 'Carlos Alberto', 'Hernández Ruiz', '+34 600 111 001', TRUE),
('TEC-002', 'Laura Patricia', 'Díaz Gómez', '+34 600 111 002', TRUE),

-- Nivel 2 (Especialistas)
('TEC-003', 'Miguel Ángel', 'Torres López', '+34 600 111 003', TRUE),
('TEC-004', 'Carmen Rosa', 'Moreno Sánchez', '+34 600 111 004', TRUE),
('TEC-005', 'Andrés Felipe', 'Ramírez Castro', '+34 600 111 005', TRUE),

-- Nivel 3 (Arquitectos/Expertos)
('TEC-006', 'Roberto Carlos', 'García Mendoza', '+34 600 111 006', TRUE),
('TEC-007', 'Elena Patricia', 'López Fernández', '+34 600 111 007', TRUE),

-- Supervisores y Administradores
('TEC-008', 'Fernando José', 'Vega Ortiz', '+34 600 111 008', TRUE),
('TEC-009', 'Natalia', 'Ortiz Romero', '+34 600 111 009', TRUE);

-- =====================================================
-- 4. TICKETS (Casos de soporte)
-- =====================================================

-- Tickets activos (abiertos y en proceso)
INSERT INTO ticket (numero_ticket, titulo, descripcion, estado, prioridad, cliente_id, categoria_id, fecha_creacion, is_activo) VALUES
                                                                                                                                 ('INC2024001', 'PC no enciende después de actualización', 'La computadora no arranca después de instalar actualizaciones de Windows', 'ABIERTO', 'ALTA', 1, 1, DATE_SUB(NOW(), INTERVAL 5 DAY), TRUE),
                                                                                                                                 ('INC2024002', 'Impresora no imprime en red', 'La impresora compartida no responde a las solicitudes de impresión', 'ABIERTO', 'MEDIA', 2, 2, DATE_SUB(NOW(), INTERVAL 3 DAY), TRUE),
                                                                                                                                 ('INC2024003', 'VPN caída - usuarios remotos', 'Los empleados no pueden conectar a la VPN desde casa', 'EN_PROCESO', 'CRITICA', 4, 8, DATE_SUB(NOW(), INTERVAL 2 DAY), TRUE),
                                                                                                                                 ('INC2024004', 'Base de datos lenta', 'Las consultas a producción están tomando más de 30 segundos', 'EN_PROCESO', 'ALTA', 6, 11, DATE_SUB(NOW(), INTERVAL 2 DAY), TRUE),
                                                                                                                                 ('INC2024005', 'Error de autenticación', 'Usuarios no pueden iniciar sesión en el ERP', 'EN_PROCESO', 'CRITICA', 7, 14, DATE_SUB(NOW(), INTERVAL 1 DAY), TRUE),

-- Tickets resueltos/cerrados
                                                                                                                                 ('INC2024006', 'Problema con correo saliente', 'Los correos no se envían correctamente', 'RESUELTO', 'MEDIA', 3, 15, DATE_SUB(NOW(), INTERVAL 10 DAY), TRUE),
                                                                                                                                 ('INC2024007', 'Backup fallido', 'El backup nocturno no se completa', 'CERRADO', 'ALTA', 5, 14, DATE_SUB(NOW(), INTERVAL 15 DAY), TRUE),
                                                                                                                                 ('INC2024008', 'Lentitud en sitio web', 'El sitio corporativo carga muy lento', 'RESUELTO', 'MEDIA', 8, 16, DATE_SUB(NOW(), INTERVAL 7 DAY), TRUE),

-- Tickets en espera
                                                                                                                                 ('INC2024009', 'Actualizar certificados SSL', 'Los certificados SSL expiran en 5 días', 'EN_ESPERA', 'ALTA', 9, 16, DATE_SUB(NOW(), INTERVAL 3 DAY), TRUE),
                                                                                                                                 ('INC2024010', 'Migración de servidor', 'Planificar migración de servidor de BD', 'EN_ESPERA', 'BAJA', 10, 3, DATE_SUB(NOW(), INTERVAL 4 DAY), TRUE);

-- =====================================================
-- 5. ASIGNAR TÉCNICOS A TICKETS
-- =====================================================
UPDATE ticket SET tecnico_asignado_id = 1 WHERE id = 1;  -- Carlos a ticket 1
UPDATE ticket SET tecnico_asignado_id = 4 WHERE id = 3;  -- Carmen a ticket 3
UPDATE ticket SET tecnico_asignado_id = 5 WHERE id = 4;  -- Andrés a ticket 4
UPDATE ticket SET tecnico_asignado_id = 7 WHERE id = 5;  -- Elena a ticket 5
UPDATE ticket SET tecnico_asignado_id = 3 WHERE id = 6;  -- Miguel a ticket 6
UPDATE ticket SET tecnico_asignado_id = 6 WHERE id = 8;  -- Roberto a ticket 8
UPDATE ticket SET tecnico_asignado_id = 4 WHERE id = 9;  -- Carmen a ticket 9
UPDATE ticket SET tecnico_asignado_id = 3 WHERE id = 10; -- Miguel a ticket 10

-- Actualizar fechas de cierre para tickets resueltos/cerrados
UPDATE ticket SET fecha_cierre = DATE_SUB(NOW(), INTERVAL 8 DAY), tiempo_resolucion_horas = 48 WHERE id = 6;
UPDATE ticket SET fecha_cierre = DATE_SUB(NOW(), INTERVAL 12 DAY), tiempo_resolucion_horas = 72 WHERE id = 7;
UPDATE ticket SET fecha_cierre = DATE_SUB(NOW(), INTERVAL 5 DAY), tiempo_resolucion_horas = 36 WHERE id = 8;

-- =====================================================
-- 6. COMENTARIOS (Seguimiento de tickets)
-- =====================================================

-- Comentarios para ticket 1 (PC no enciende)
INSERT INTO comentario (contenido, es_interno, ticket_id, cliente_id, fecha_creacion) VALUES
    ('Buen día, mi PC no enciende desde la actualización de Windows', FALSE, 1, 1, DATE_SUB(NOW(), INTERVAL 5 DAY));

INSERT INTO comentario (contenido, es_interno, ticket_id, tecnico_id, fecha_creacion) VALUES
    ('Revisando el caso, se programará visita técnica', TRUE, 1, 1, DATE_SUB(NOW(), INTERVAL 4 DAY));

-- Comentarios para ticket 3 (VPN)
INSERT INTO comentario (contenido, es_interno, ticket_id, cliente_id, fecha_creacion) VALUES
    ('El problema es muy grave, afecta a toda la empresa', FALSE, 3, 4, DATE_SUB(NOW(), INTERVAL 2 DAY));

INSERT INTO comentario (contenido, es_interno, ticket_id, tecnico_id, fecha_creacion) VALUES
    ('Inicio investigación del problema de VPN', TRUE, 3, 4, DATE_SUB(NOW(), INTERVAL 2 DAY));

INSERT INTO comentario (contenido, es_interno, ticket_id, tecnico_id, fecha_creacion) VALUES
    ('Contactando con proveedor de internet', TRUE, 3, 4, DATE_SUB(NOW(), INTERVAL 1 DAY));

INSERT INTO comentario (contenido, es_interno, ticket_id, cliente_id, fecha_creacion) VALUES
    ('Sigue sin funcionar la VPN, necesito una solución urgente', FALSE, 3, 4, DATE_SUB(NOW(), INTERVAL 1 DAY));

INSERT INTO comentario (contenido, es_interno, ticket_id, tecnico_id, fecha_creacion) VALUES
    ('¿Podría confirmar si el problema persiste?', FALSE, 3, 4, DATE_SUB(NOW(), INTERVAL 12 HOUR));

-- Comentarios para ticket 4 (Base de datos)
INSERT INTO comentario (contenido, es_interno, ticket_id, cliente_id, fecha_creacion) VALUES
    ('Las consultas están muy lentas, el sistema está casi inoperable', FALSE, 4, 6, DATE_SUB(NOW(), INTERVAL 2 DAY));

INSERT INTO comentario (contenido, es_interno, ticket_id, tecnico_id, fecha_creacion) VALUES
    ('Analizando queries lentas', TRUE, 4, 5, DATE_SUB(NOW(), INTERVAL 1 DAY));

INSERT INTO comentario (contenido, es_interno, ticket_id, tecnico_id, fecha_creacion) VALUES
    ('Se identificaron índices faltantes', TRUE, 4, 5, DATE_SUB(NOW(), INTERVAL 6 HOUR));

INSERT INTO comentario (contenido, es_interno, ticket_id, tecnico_id, fecha_creacion) VALUES
    ('Optimización aplicada, mejoró un 80% el rendimiento', FALSE, 4, 5, NOW());

-- Comentarios para ticket 5 (Error autenticación)
INSERT INTO comentario (contenido, es_interno, ticket_id, cliente_id, fecha_creacion) VALUES
    ('Los usuarios no pueden iniciar sesión, es crítico', FALSE, 5, 7, DATE_SUB(NOW(), INTERVAL 1 DAY));

INSERT INTO comentario (contenido, es_interno, ticket_id, tecnico_id, fecha_creacion) VALUES
    ('Revisando logs de autenticación', TRUE, 5, 7, DATE_SUB(NOW(), INTERVAL 23 HOUR));

INSERT INTO comentario (contenido, es_interno, ticket_id, tecnico_id, fecha_creacion) VALUES
    ('Se detectó un problema con el LDAP', TRUE, 5, 7, DATE_SUB(NOW(), INTERVAL 12 HOUR));

-- Comentarios para ticket 6 (Correo saliente - resuelto)
INSERT INTO comentario (contenido, es_interno, ticket_id, cliente_id, fecha_creacion) VALUES
    ('Los correos no salen, lleva 2 días así', FALSE, 6, 3, DATE_SUB(NOW(), INTERVAL 10 DAY));

INSERT INTO comentario (contenido, es_interno, ticket_id, tecnico_id, fecha_creacion) VALUES
    ('Revisando configuración del servidor SMTP', TRUE, 6, 3, DATE_SUB(NOW(), INTERVAL 9 DAY));

INSERT INTO comentario (contenido, es_interno, ticket_id, tecnico_id, fecha_creacion) VALUES
    ('Se reinició el servicio de correo', TRUE, 6, 3, DATE_SUB(NOW(), INTERVAL 9 DAY));

INSERT INTO comentario (contenido, es_interno, ticket_id, cliente_id, fecha_creacion) VALUES
    ('Excelente servicio, problema resuelto', FALSE, 6, 3, DATE_SUB(NOW(), INTERVAL 8 DAY));

-- Comentarios para ticket 8 (Sitio web lento)
INSERT INTO comentario (contenido, es_interno, ticket_id, cliente_id, fecha_creacion) VALUES
    ('El sitio web está muy lento, los clientes se quejan', FALSE, 8, 8, DATE_SUB(NOW(), INTERVAL 7 DAY));

INSERT INTO comentario (contenido, es_interno, ticket_id, tecnico_id, fecha_creacion) VALUES
    ('Optimizando caché del servidor web', TRUE, 8, 6, DATE_SUB(NOW(), INTERVAL 6 DAY));

INSERT INTO comentario (contenido, es_interno, ticket_id, tecnico_id, fecha_creacion) VALUES
    ('Se mejoró la configuración del CDN', FALSE, 8, 6, DATE_SUB(NOW(), INTERVAL 5 DAY));

INSERT INTO comentario (contenido, es_interno, ticket_id, cliente_id, fecha_creacion) VALUES
    ('El sitio funciona mucho mejor, gracias', FALSE, 8, 8, DATE_SUB(NOW(), INTERVAL 5 DAY));

-- =====================================================
-- 7. REGISTRO DE CAMBIOS (Historial)
-- =====================================================
INSERT INTO historial_ticket (ticket_id, campo_modificado, valor_anterior, valor_nuevo, usuario_modifico, fecha_modificacion) VALUES
                                                                                                                                  (3, 'estado', 'ABIERTO', 'EN_PROCESO', 'carmen.moreno@soporte.com', DATE_SUB(NOW(), INTERVAL 2 DAY)),
                                                                                                                                  (3, 'prioridad', 'ALTA', 'CRITICA', 'fernando.vega@soporte.com', DATE_SUB(NOW(), INTERVAL 1 DAY)),
                                                                                                                                  (4, 'estado', 'ABIERTO', 'EN_PROCESO', 'andres.ramirez@soporte.com', DATE_SUB(NOW(), INTERVAL 1 DAY)),
                                                                                                                                  (5, 'prioridad', 'MEDIA', 'CRITICA', 'elena.lopez@soporte.com', DATE_SUB(NOW(), INTERVAL 23 HOUR)),
                                                                                                                                  (6, 'estado', 'ABIERTO', 'EN_PROCESO', 'miguel.torres@soporte.com', DATE_SUB(NOW(), INTERVAL 9 DAY)),
                                                                                                                                  (6, 'estado', 'EN_PROCESO', 'RESUELTO', 'miguel.torres@soporte.com', DATE_SUB(NOW(), INTERVAL 8 DAY)),
                                                                                                                                  (8, 'estado', 'ABIERTO', 'EN_PROCESO', 'roberto.garcia@soporte.com', DATE_SUB(NOW(), INTERVAL 6 DAY)),
                                                                                                                                  (8, 'estado', 'EN_PROCESO', 'RESUELTO', 'roberto.garcia@soporte.com', DATE_SUB(NOW(), INTERVAL 5 DAY)),
                                                                                                                                  (9, 'estado', 'ABIERTO', 'EN_ESPERA', 'carmen.moreno@soporte.com', DATE_SUB(NOW(), INTERVAL 2 DAY)),
                                                                                                                                  (10, 'estado', 'ABIERTO', 'EN_ESPERA', 'miguel.torres@soporte.com', DATE_SUB(NOW(), INTERVAL 3 DAY));

-- =====================================================
-- 8. USUARIOS (Para autenticación)
-- =====================================================
-- Nota: Las contraseñas deben estar encriptadas en producción
-- Los valores aquí son ejemplos con bcrypt (password = "123456" para todos)
INSERT INTO usuario (nombre, clave, rol, is_actived, is_account_non_locked, is_account_non_expired, is_credentials_non_expired, created_at) VALUES
                                                                                                                                                ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', 'ADMIN', TRUE, TRUE, TRUE, TRUE, NOW()),
                                                                                                                                                ('jc.perez', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', 'CLIENTE', TRUE, TRUE, TRUE, TRUE, NOW()),
                                                                                                                                                ('md.gonzalez', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', 'CLIENTE', TRUE, TRUE, TRUE, TRUE, NOW()),
                                                                                                                                                ('ca.hernandez', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', 'TECNICO', TRUE, TRUE, TRUE, TRUE, NOW()),
                                                                                                                                                ('ma.torres', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', 'TECNICO', TRUE, TRUE, TRUE, TRUE, NOW()),
                                                                                                                                                ('cr.moreno', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', 'TECNICO', TRUE, TRUE, TRUE, TRUE, NOW()),
                                                                                                                                                ('af.ramirez', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', 'TECNICO', TRUE, TRUE, TRUE, TRUE, NOW()),
                                                                                                                                                ('rc.garcia', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', 'TECNICO', TRUE, TRUE, TRUE, TRUE, NOW()),
                                                                                                                                                ('ep.lopez', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', 'TECNICO', TRUE, TRUE, TRUE, TRUE, NOW()),
                                                                                                                                                ('fj.vega', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', 'SUPERVISOR', TRUE, TRUE, TRUE, TRUE, NOW());

-- =====================================================
-- VERIFICACIÓN DE DATOS INSERTADOS
-- =====================================================
SELECT '=== DATOS INSERTADOS CORRECTAMENTE ===' AS Mensaje;
SELECT 'Categorías' as Tabla, COUNT(*) as Registros FROM categoria
UNION SELECT 'Clientes', COUNT(*) FROM cliente
UNION SELECT 'Técnicos', COUNT(*) FROM tecnico
UNION SELECT 'Tickets', COUNT(*) FROM ticket
UNION SELECT 'Comentarios', COUNT(*) FROM comentario
UNION SELECT 'Historial', COUNT(*) FROM historial_ticket
UNION SELECT 'Usuarios', COUNT(*) FROM usuario;

-- Mostrar resumen de tickets
SELECT '=== RESUMEN DE TICKETS ===' AS Info;
SELECT estado, COUNT(*) as Cantidad FROM ticket GROUP BY estado;

SELECT prioridad, COUNT(*) as Cantidad FROM ticket GROUP BY prioridad;