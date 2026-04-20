-- =====================================================
-- MANUAL - CREACIÓN DE VISTAS
-- =====================================================
-- Título: Vistas para reportes y dashboards
-- Propósito: Facilitar consultas complejas y reporting
-- Uso: mysql -u root -p soporte_tecnico < 03_create_views.sql
-- =====================================================

-- =====================================================
-- VISTA 1: Dashboard ejecutivo
-- =====================================================
DROP VIEW IF EXISTS vw_dashboard_ejecutivo;
CREATE VIEW vw_dashboard_ejecutivo AS
SELECT
    'Métricas Generales' AS seccion,
    (SELECT COUNT(*) FROM tickets WHERE activo = TRUE) AS total_tickets,
    (SELECT COUNT(*) FROM tickets WHERE estado = 'ABIERTO') AS abiertos,
    (SELECT COUNT(*) FROM tickets WHERE estado = 'EN_PROCESO') AS en_proceso,
    (SELECT COUNT(*) FROM tickets WHERE estado = 'RESUELTO') AS resueltos,
    (SELECT COUNT(*) FROM tickets WHERE estado = 'CERRADO') AS cerrados,
    (SELECT COUNT(*) FROM tickets WHERE prioridad = 'CRITICA' AND estado NOT IN ('CERRADO', 'RESUELTO')) AS criticos_activos,
    (SELECT ROUND(AVG(tiempo_resolucion_horas), 2) FROM ticket WHERE tiempo_resolucion_horas IS NOT NULL) AS tiempo_promedio_resolucion,
    (SELECT COUNT(*) FROM clientes WHERE activo = TRUE) AS clientes_activos,
    (SELECT COUNT(*) FROM tecnicos WHERE activo = TRUE) AS tecnicos_activos;

-- =====================================================
-- VISTA 2: Tickets detallados (con joins)
-- =====================================================
DROP VIEW IF EXISTS vw_tickets_detalle;
CREATE VIEW vw_tickets_detalle AS
SELECT
    t.id,
    t.numero_ticket,
    t.titulo,
    SUBSTRING(t.descripcion, 1, 100) AS descripcion_resumida,
    t.estado,
    t.prioridad,
    -- Cliente
    CONCAT(c.nombre, ' ', c.apellido) AS cliente_nombre,
    c.email AS cliente_email,
    c.empresa,
    -- Técnico
    CONCAT(tec.nombre, ' ', tec.apellido) AS tecnico_nombre,
    tec.rol AS tecnico_rol,
    -- Categoría
    cat.nombre AS categoria,
    -- Fechas
    DATE_FORMAT(t.fecha_creacion, '%Y-%m-%d %H:%i') AS fecha_creacion,
    DATE_FORMAT(t.fecha_actualizacion, '%Y-%m-%d %H:%i') AS fecha_actualizacion,
    DATE_FORMAT(t.fecha_cierre, '%Y-%m-%d %H:%i') AS fecha_cierre,
    -- Tiempos
    t.tiempo_resolucion_horas,
    TIMESTAMPDIFF(HOUR, t.fecha_creacion, NOW()) AS horas_transcurridas,
    -- Estado texto
    CASE t.estado
        WHEN 'ABIERTO' THEN '🟡 Abierto - Pendiente'
        WHEN 'EN_PROCESO' THEN '🔵 En proceso - Atendiendo'
        WHEN 'EN_ESPERA' THEN '⏳ En espera - Requiere información'
        WHEN 'RESUELTO' THEN '✅ Resuelto - Pendiente confirmación'
        WHEN 'CERRADO' THEN '✔️ Cerrado - Finalizado'
        WHEN 'CANCELADO' THEN '❌ Cancelado'
        END AS estado_descripcion,
    -- Indicador visual de prioridad
    CASE t.prioridad
        WHEN 'CRITICA' THEN '🔴 CRÍTICA'
        WHEN 'ALTA' THEN '🟠 ALTA'
        WHEN 'MEDIA' THEN '🟡 MEDIA'
        WHEN 'BAJA' THEN '🟢 BAJA'
        END AS prioridad_visual
FROM ticket t
         LEFT JOIN cliente c ON t.cliente_id = c.id
         LEFT JOIN tecnico tec ON t.tecnico_asignado_id = tec.id