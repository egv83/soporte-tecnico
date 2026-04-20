-- =====================================================
-- MANUAL - CARGA DE DATOS INICIALES
-- =====================================================
-- Título: Script de inserción de datos semilla
-- Propósito: Poblar la base con datos iniciales necesarios
-- Uso: mysql -u root -p soporte_tecnico < 02_insert_seed_data.sql
-- =====================================================

-- =====================================================
-- 1. CATEGORÍAS (Datos base del sistema)
-- =====================================================
INSERT INTO categoria (nombre, descripcion, tiempo_estimado_horas) VALUES
-- Categorías de hardware
('Hardware - PCs', 'Problemas con computadoras de escritorio, laptops, componentes internos', 24),
('Hardware - Periféricos', 'Problemas con impresoras, scanners, monitores, teclados, mouse', 12),
('Hardware - Servidores', 'Problemas con servidores físicos, blades, racks', 48),

-- Categorías de software
('Software - SO', 'Problemas con Windows, Linux, macOS', 16),
('Software - Aplicaciones', 'Problemas con software ofimático, ERP, CRM', 24),
('Software - Desarrollo', 'Problemas con IDEs, compiladores, control de versiones', 36),

-- Categorías de redes
('Redes - LAN', 'Problemas de conectividad local, switches, routers internos', 48),
('Redes - WAN/VPN', 'Problemas de conectividad remota, VPN, MPLS', 72),
('Redes - WiFi', 'Problemas de conectividad inalámbrica', 24),

-- Categorías de bases de datos
('BD - MySQL', 'Problemas específicos de MySQL/MariaDB', 36),
('BD - PostgreSQL', 'Problemas específicos de PostgreSQL', 36),
('BD - SQL Server', 'Problemas específicos de SQL Server', 48),

-- Otras categorías
('Seguridad', 'Problemas de seguridad, permisos, accesos', 72),
('Backup', 'Problemas con copias de seguridad y restauración', 48),
('Correo', 'Problemas con servidores y clientes de correo', 24),
('Sitios Web', 'Problemas con páginas web, hosting, dominios', 48);

-- =====================================================
-- 2. CLIENTES (Usuarios del sistema)
-- =====================================================
INSERT INTO cliente (nombre, apellido, email, telefono, empresa) VALUES
-- Empresa Tech Solutions
('Juan Carlos', 'Pérez Rodríguez', 'jc.perez@techsolutions.com', '+34 911 234 567', 'Tech Solutions S.A.'),
('María Dolores', 'González López', 'md.gonzalez@techsolutions.com', '+34 911 234 568', 'Tech Solutions S.A.'),
('Luis Miguel', 'Sánchez García', 'lm.sanchez@techsolutions.com', '+34 911 234 569', 'Tech Solutions S.A.'),

-- Empresa Digital Innovations
('Ana Belén', 'Martínez Ruiz', 'ab.martinez@digitalinnovations.com', '+34 922 345 678', 'Digital Innovations S.L.'),
('Carlos Javier', 'Rodríguez Fernández', 'cj.rodriguez@digitalinnovations.com', '+34 922 345 679', 'Digital Innovations S.L.'),

-- Empresa Data Center
('Elena María', 'López Díaz', 'em.lopez@datacenter.com', '+34 933 456 789', 'Data Center Corp'),
('David', 'Fernández Gómez', 'd.fernandez@datacenter.com', '+34 933 456 790', 'Data Center Corp'),

-- Otras empresas
('Sofía', 'Torres Mendoza', 'sofia.torres@cloudservices.com', '+34 944 567 890', 'Cloud Services S.A.'),
('Javier', 'Flores Ramírez', 'j.flores@startup.com', '+34 955 678 901', 'Startup Innovations'),
('Patricia', 'Mendoza Castro', 'p.mendoza@negocio.com', '+34 966 789 012', 'Negocio Digital S.L.');

-- =====================================================
-- 3. TÉCNICOS (Personal de soporte)
-- =====================================================
INSERT INTO tecnico (nombre, apellido, email, telefono, rol, especialidad) VALUES
-- Nivel 1 (Soporte inicial)
('Carlos Alberto', 'Hernández Ruiz', 'ca.hernandez@soporte.com', '+34 600 111 001', 'SOPORTE_NIVEL1', 'Soporte general, Windows, Office'),
('Laura Patricia', 'Díaz Gómez', 'lp.diaz@soporte.com', '+34 600 111 002', 'SOPORTE_NIVEL1', 'Hardware, periféricos, usuarios'),

-- Nivel 2 (Especialistas)
('Miguel Ángel', 'Torres López', 'ma.torres@soporte.com', '+34 600 111 003', 'SOPORTE_NIVEL2', 'Redes, conectividad, VPN'),
('Carmen Rosa', 'Moreno Sánchez', 'cr.moreno@soporte.com', '+34 600 111 004', 'SOPORTE_NIVEL2', 'Software, aplicaciones empresariales'),
('Andrés Felipe', 'Ramírez Castro', 'af.ramirez@soporte.com', '+34 600 111 005', 'SOPORTE_NIVEL2', 'Base de datos, consultas SQL'),

-- Nivel 3 (Arquitectos/Expertos)
('Roberto Carlos', 'García Mendoza', 'rc.garcia@soporte.com', '+34 600 111 006', 'SOPORTE_NIVEL3', 'Servidores, infraestructura, cloud'),
('Elena Patricia', 'López Fernández', 'ep.lopez@soporte.com', '+34 600 111 007', 'SOPORTE_NIVEL3', 'Seguridad, firewalls, auditoría'),

-- Supervisores y Administradores
('Fernando José', 'Vega Ortiz', 'fj.vega@soporte.com', '+34 600 111 008', 'SUPERVISOR', 'Gestión de equipos, procesos'),
('Natalia', 'Ortiz Romero', 'n.ortiz@soporte.com', '+34 600 111 009', 'ADMINISTRADOR', 'Administración del sistema');

-- =====================================================
-- 4. TICKETS (Casos de soporte)
-- =====================================================
-- Función auxiliar para generar números de ticket
-- Nota: En producción usaría un trigger o secuencia

-- Tickets activos (abiertos y en proceso)
INSERT INTO ticket (numero_ticket, titulo, descripcion, estado, prioridad, cliente_id, categoria_id) VALUES
                                                                                                          ('INC2024001', 'PC no enciende después de actualización', 'La computadora no arranca después de instalar actualizaciones de Windows', 'ABIERTO', 'ALTA', 1, 1),
                                                                                                          ('INC2024002', 'Impresora no imprime en red', 'La impresora compartida no responde a las solicitudes de impresión', 'ABIERTO', 'MEDIA', 2, 2),
                                                                                                          ('INC2024003', 'VPN caída - usuarios remotos', 'Los empleados no pueden conectar a la VPN desde casa', 'EN_PROCESO', 'CRITICA', 4, 8),
                                                                                                          ('INC2024004', 'Base de datos lenta', 'Las consultas a producción están tomando más de 30 segundos', 'EN_PROCESO', 'ALTA', 6, 11),
                                                                                                          ('INC2024005', 'Error de autenticación', 'Usuarios no pueden iniciar sesión en el ERP', 'EN_PROCESO', 'CRITICA', 7, 14),

-- Tickets resueltos/cerrados
                                                                                                          ('INC2024006', 'Problema con correo saliente', 'Los correos no se envían correctamente', 'RESUELTO', 'MEDIA', 3, 15),
                                                                                                          ('INC2024007', 'Backup fallido', 'El backup nocturno no se completa', 'CERRADO', 'ALTA', 5, 14),
                                                                                                          ('INC2024008', 'Lentitud en sitio web', 'El sitio corporativo carga muy lento', 'RESUELTO', 'MEDIA', 8, 16),

-- Tickets en espera
                                                                                                          ('INC2024009', 'Actualizar certificados SSL', 'Los certificados SSL expiran en 5 días', 'EN_ESPERA', 'ALTA', 9, 16),
                                                                                                          ('INC2024010', 'Migración de servidor', 'Planificar migración de servidor de BD', 'EN_ESPERA', 'BAJA', 10, 3);

-- =====================================================
-- 5. ASIGNAR TÉCNICOS A TICKETS
-- =====================================================
UPDATE ticket SET tecnico_asignado_id = 1 WHERE id = 1;  -- Carlos a ticket 1
UPDATE ticket SET tecnico_asignado_id = 4 WHERE id = 3;  -- Carmen a ticket 3
UPDATE ticket SET tecnico_asignado_id = 5 WHERE id = 4;  -- Andrés a ticket 4
UPDATE ticket SET tecnico_asignado_id = 7 WHERE id = 5;  -- Elena a ticket 5
UPDATE ticket SET tecnico_asignado_id = 3 WHERE id = 6;  -- Miguel a ticket 6
UPDATE ticket SET tecnico_asignado_id = 6 WHERE id = 8;  -- Roberto a ticket 8

-- =====================================================
-- 6. COMENTARIOS (Seguimiento de tickets)
-- =====================================================
-- Comentarios para ticket 3 (VPN)
INSERT INTO comentario (contenido, es_interno, ticket_id, tecnico_id, fecha_creacion) VALUES
                                                                                           ('Inicio investigación del problema de VPN', TRUE, 3, 4, DATE_SUB(NOW(), INTERVAL 2 DAY)),
                                                                                           ('Contactando con proveedor de internet', TRUE, 3, 4, DATE_SUB(NOW(), INTERVAL 1 DAY)),
                                                                                           ('¿Podría confirmar si el problema persiste?', FALSE, 3, 4, DATE_SUB(NOW(), INTERVAL 12 HOUR));

-- Comentarios para ticket 4 (Base de datos)
INSERT INTO comentario (contenido, es_interno, ticket_id, tecnico_id, fecha_creacion) VALUES
                                                                                           ('Analizando queries lentas', TRUE, 4, 5, DATE_SUB(NOW(), INTERVAL 1 DAY)),
                                                                                           ('Se identificaron índices faltantes', TRUE, 4, 5, DATE_SUB(NOW(), INTERVAL 6 HOUR)),
                                                                                           ('Optimización aplicada, mejoró un 80% el rendimiento', FALSE, 4, 5, NOW());

-- Comentarios de clientes
INSERT INTO comentario (contenido, es_interno, ticket_id, cliente_id, fecha_creacion) VALUES
                                                                                           ('El problema es muy grave, afecta a toda la empresa', FALSE, 3, 4, DATE_SUB(NOW(), INTERVAL 2 DAY)),
                                                                                           ('Sigue sin funcionar la VPN', FALSE, 3, 4, DATE_SUB(NOW(), INTERVAL 1 DAY)),
                                                                                           ('Excelente servicio, problema resuelto', FALSE, 6, 3, DATE_SUB(NOW(), INTERVAL 1 DAY));

-- =====================================================
-- 7. REGISTRO DE CAMBIOS (Historial)
-- =====================================================
INSERT INTO historial_ticket (ticket_id, campo_modificado, valor_anterior, valor_nuevo, usuario_modifico) VALUES
                                                                                                               (3, 'estado', 'ABIERTO', 'EN_PROCESO', 'carmen.moreno@soporte.com'),
                                                                                                               (3, 'prioridad', 'ALTA', 'CRITICA', 'fernando.vega@soporte.com'),
                                                                                                               (4, 'estado', 'ABIERTO', 'EN_PROCESO', 'andres.ramirez@soporte.com'),
                                                                                                               (5, 'prioridad', 'MEDIA', 'CRITICA', 'elena.lopez@soporte.com'),
                                                                                                               (6, 'estado', 'ABIERTO', 'RESUELTO', 'miguel.torres@soporte.com');

-- =====================================================
-- VERIFICACIÓN DE DATOS INSERTADOS
-- =====================================================
SELECT '=== DATOS INSERTADOS CORRECTAMENTE ===' AS Mensaje;
SELECT 'Categorías' as Tabla, COUNT(*) as Registros FROM categoria
UNION SELECT 'Clientes', COUNT(*) FROM cliente
UNION SELECT 'Técnicos', COUNT(*) FROM tecnico
UNION SELECT 'Tickets', COUNT(*) FROM ticket
UNION SELECT 'Comentarios', COUNT(*) FROM comentario
UNION SELECT 'Historial', COUNT(*) FROM historial_ticket;