-- Script de Demostración y Pruebas para Smart Energy (PostgreSQL)
-- Este script limpia la base de datos e inserta un escenario completo, realista y dinámico.
-- Utiliza fechas y horas relativas al momento de la ejecución para garantizar que los cálculos 
-- de consumo real, esperado y la generación de alertas (monitoreo) funcionen de forma inmediata.

-- 1. Limpieza de base de datos completa respetando restricciones de claves foráneas
TRUNCATE TABLE 
    registro_operativo, 
    alerta, 
    incidencia, 
    horario_academico, 
    equipo, 
    aula, 
    asignacion_edificio, 
    edificio, 
    administrador, 
    apoyo_logistica, 
    usuario, 
    config_sistema 
CASCADE;

-- 2. Configuración del Sistema (Margen de encendido: 15 min, Tiempo mínimo desperdicio: 15 min, Activo: true)
INSERT INTO config_sistema (id, margen_encendido, tiempo_minimo_desperdicio, activo)
VALUES ('c0c0c0c0-c0c0-c0c0-c0c0-c0c0c0c0c0c0', 15, 15, true);

-- 3. Usuarios de Demostración
-- Administrador (CIF: ADMIN001 / Clave: admin123)
INSERT INTO usuario (id, activo, nombre, apellido, cif, password)
VALUES ('adadadad-adad-adad-adad-adadadadadad', true, 'Adriano', 'Almanza', 'ADMIN001', 'admin123');

INSERT INTO administrador (id, nivel_acceso)
VALUES ('adadadad-adad-adad-adad-adadadadadad', 'SUPER_USER');

-- Apoyo de Logística (CIF: APOYO001 / Clave: support123)
INSERT INTO usuario (id, activo, nombre, apellido, cif, password)
VALUES ('f0f0f0f0-f0f0-f0f0-f0f0-f0f0f0f0f0f0', true, 'Juan', 'Pérez', 'APOYO001', 'support123');

INSERT INTO apoyo_logistica (id)
VALUES ('f0f0f0f0-f0f0-f0f0-f0f0-f0f0f0f0f0f0');

-- 4. Edificios
INSERT INTO edificio (id, nombre) VALUES
('eaea000a-aeae-aeae-aeae-aeaeaeaeaeae', 'Edificio A (Ciencia y Tecnología)'),
('ebeb000b-bebe-bebe-bebe-bebebebebebe', 'Edificio B (Ciencias Económicas)'),
('ecec000c-cece-cece-cece-cececececece', 'Edificio C (Humanidades)');

-- 5. Asignaciones de Edificios a Personal de Logística
INSERT INTO asignacion_edificio (id, activo, fecha_asignacion, logistica_id, edificio_id) VALUES
('a0a0a0a0-0000-0000-0000-000000000000', true, CURRENT_DATE, 'f0f0f0f0-f0f0-f0f0-f0f0-f0f0f0f0f0f0', 'eaea000a-aeae-aeae-aeae-aeaeaeaeaeae'),
('b0b0b0b0-0000-0000-0000-000000000000', true, CURRENT_DATE, 'f0f0f0f0-f0f0-f0f0-f0f0-f0f0f0f0f0f0', 'ebeb000b-bebe-bebe-bebe-bebebebebebe'),
('c0c0c0c0-0000-0000-0000-000000000000', true, CURRENT_DATE, 'f0f0f0f0-f0f0-f0f0-f0f0-f0f0f0f0f0f0', 'ecec000c-cece-cece-cece-cececececece');

-- 6. Aulas
INSERT INTO aula (id, codigo, edificio_id) VALUES
('a101a101-1111-1111-1111-111111111111', 'A-101', 'eaea000a-aeae-aeae-aeae-aeaeaeaeaeae'),
('a102a102-2222-2222-2222-222222222222', 'A-102', 'eaea000a-aeae-aeae-aeae-aeaeaeaeaeae'),
('b201b201-3333-3333-3333-333333333333', 'B-201', 'ebeb000b-bebe-bebe-bebe-bebebebebebe'),
('b202b202-4444-4444-4444-444444444444', 'B-202', 'ebeb000b-bebe-bebe-bebe-bebebebebebe'),
('c301c301-5555-5555-5555-555555555555', 'C-301', 'ecec000c-cece-cece-cece-cececececece');

-- 7. Equipos de Aire Acondicionado
-- A-101: APAGADO (debería estar encendido por clase activa)
-- A-102: ENCENDIDO (desperdicio porque clase ya terminó y proxima es más tarde)
-- B-201: APAGADO (correcto, clase es en el futuro)
-- B-202: ENCENDIDO (correcto, clase activa en curso)
-- C-301: ENCENDIDO (desperdicio porque no tiene clases registradas hoy)
INSERT INTO equipo (id, marca, modelo, btu, eficiencia, operativo, potencia_minima, potencia_nominal, estado, aula_id) VALUES
('e101e101-1111-1111-1111-111111111111', 'Lennox', 'LX-12000', 12000, 'SEER 18', true, 400.0, 1200.0, 'APAGADO', 'a101a101-1111-1111-1111-111111111111'),
('e102e102-2222-2222-2222-222222222222', 'Panasonic', 'PN-18000', 18000, 'SEER 20', true, 600.0, 1800.0, 'ENCENDIDO', 'a102a102-2222-2222-2222-222222222222'),
('e201e201-3333-3333-3333-333333333333', 'Carrier', 'CR-24000', 24000, 'SEER 16', true, 800.0, 2400.0, 'APAGADO', 'b201b201-3333-3333-3333-333333333333'),
('e202e202-4444-4444-4444-444444444444', 'LG', 'LG-12000', 12000, 'SEER 22', true, 350.0, 1100.0, 'ENCENDIDO', 'b202b202-4444-4444-4444-444444444444'),
('e301e301-5555-5555-5555-555555555555', 'Samsung', 'SM-18000', 18000, 'SEER 19', true, 550.0, 1700.0, 'ENCENDIDO', 'c301c301-5555-5555-5555-555555555555');

-- 8. Horarios Académicos Dinámicos (Se ejecutan para el día de la semana actual)
-- Nota: EXTRACT(ISODOW FROM CURRENT_DATE) devuelve 1 para lunes y 7 para domingo.
INSERT INTO horario_academico (id, asignatura, dia_semana, hora_inicio, hora_fin, aula_id) VALUES
-- Aula A-101: Clase en curso (inició hace 30m, termina en 90m)
('ab01ab01-1111-1111-1111-111111111111', 'Introducción a la Programación', 
 EXTRACT(ISODOW FROM CURRENT_DATE)::integer, 
 (CURRENT_TIMESTAMP - INTERVAL '30 minutes')::time, 
 (CURRENT_TIMESTAMP + INTERVAL '90 minutes')::time, 
 'a101a101-1111-1111-1111-111111111111'),

-- Aula A-102: Clase pasada (terminó hace 2 horas) y clase futura (inicia en 1 hora) para simular desperdicio
('ab02ab02-2222-2222-2222-222222222222', 'Cálculo I (Clase Pasada)', 
 EXTRACT(ISODOW FROM CURRENT_DATE)::integer, 
 (CURRENT_TIMESTAMP - INTERVAL '4 hours')::time, 
 (CURRENT_TIMESTAMP - INTERVAL '2 hours')::time, 
 'a102a102-2222-2222-2222-222222222222'),
('ab02ab02-2222-2222-2222-333333333333', 'Física General (Clase Futura)', 
 EXTRACT(ISODOW FROM CURRENT_DATE)::integer, 
 (CURRENT_TIMESTAMP + INTERVAL '1 hour')::time, 
 (CURRENT_TIMESTAMP + INTERVAL '3 hours')::time, 
 'a102a102-2222-2222-2222-222222222222'),

-- Aula B-201: Clase futura (inicia en 1 hora)
('bb01bb01-3333-3333-3333-333333333333', 'Macroeconomía', 
 EXTRACT(ISODOW FROM CURRENT_DATE)::integer, 
 (CURRENT_TIMESTAMP + INTERVAL '60 minutes')::time, 
 (CURRENT_TIMESTAMP + INTERVAL '180 minutes')::time, 
 'b201b201-3333-3333-3333-333333333333'),

-- Aula B-202: Clase en curso (inició hace 60m, termina en 60m)
('bb02bb02-4444-4444-4444-444444444444', 'Contabilidad Financiera', 
 EXTRACT(ISODOW FROM CURRENT_DATE)::integer, 
 (CURRENT_TIMESTAMP - INTERVAL '60 minutes')::time, 
 (CURRENT_TIMESTAMP + INTERVAL '60 minutes')::time, 
 'b202b202-4444-4444-4444-444444444444'),

-- Aula C-301: Sin clases hoy. Le asignamos una clase mañana para que tenga historial pero ninguna hoy
('cb01cb01-5555-5555-5555-555555555555', 'Filosofía General (Mañana)', 
 (EXTRACT(ISODOW FROM CURRENT_DATE)::integer % 7) + 1, 
 '08:00:00'::time, 
 '10:00:00'::time, 
 'c301c301-5555-5555-5555-555555555555');

-- 9. Registro Operativo Histórico y Actual
-- Para que los cálculos del Dashboard muestren datos válidos y continuos.
-- Nota: Los registros activos tienen fin = NULL y consumo = 0.0.

-- Aula A-101 (Apagado todo el día)
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id) VALUES
('da01da01-0000-0000-0000-000000000000', 'APAGADO', 0.0, CURRENT_DATE::timestamp, NULL, 'e101e101-1111-1111-1111-111111111111');

-- Aula A-102 (Apagado de 0:00 hasta hace 4 hours; encendido desde hace 4 hours hasta ahora)
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id) VALUES
('da02da02-1111-1111-1111-111111111111', 'APAGADO', 0.0, 
 CURRENT_DATE::timestamp, 
 (CURRENT_TIMESTAMP - INTERVAL '4 hours'), 
 'e102e102-2222-2222-2222-222222222222'),
('da02da02-2222-2222-2222-222222222222', 'ENCENDIDO', 0.0, 
 (CURRENT_TIMESTAMP - INTERVAL '4 hours'), 
 NULL, 
 'e102e102-2222-2222-2222-222222222222');

-- Aula B-201 (Apagado todo el día)
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id) VALUES
('db01db01-0000-0000-0000-000000000000', 'APAGADO', 0.0, CURRENT_DATE::timestamp, NULL, 'e201e201-3333-3333-3333-333333333333');

-- Aula B-202 (Apagado de 0:00 hasta hace 1 hour; encendido desde hace 1 hour hasta ahora)
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id) VALUES
('db02db02-1111-1111-1111-111111111111', 'APAGADO', 0.0, 
 CURRENT_DATE::timestamp, 
 (CURRENT_TIMESTAMP - INTERVAL '1 hour'), 
 'e202e202-4444-4444-4444-444444444444'),
('db02db02-2222-2222-2222-222222222222', 'ENCENDIDO', 0.0, 
 (CURRENT_TIMESTAMP - INTERVAL '1 hour'), 
 NULL, 
 'e202e202-4444-4444-4444-444444444444');

-- Aula C-301 (Apagado de 0:00 hasta hace 30 minutos; encendido desde hace 30 minutos hasta ahora)
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id) VALUES
('dc01dc01-1111-1111-1111-111111111111', 'APAGADO', 0.0, 
 CURRENT_DATE::timestamp, 
 (CURRENT_TIMESTAMP - INTERVAL '30 minutes'), 
 'e301e301-5555-5555-5555-555555555555'),
('dc01dc01-2222-2222-2222-222222222222', 'ENCENDIDO', 0.0, 
 (CURRENT_TIMESTAMP - INTERVAL '30 minutes'), 
 NULL, 
 'e301e301-5555-5555-5555-555555555555');
