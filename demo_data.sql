-- ==========================================
-- SCRIPT DE DEMOSTRACIÓN FUNCIONAL - SmartEnergy
-- PostgreSQL Database Seed Script (Escenario Realista de Alertas y Ahorros)
-- ==========================================

-- 1. Limpieza de datos existentes (respetando restricciones de integridad referencial)
TRUNCATE TABLE asignacion_edificio CASCADE;
TRUNCATE TABLE registro_operativo CASCADE;
TRUNCATE TABLE horario_academico CASCADE;
TRUNCATE TABLE alerta CASCADE;
TRUNCATE TABLE incidencia CASCADE;
TRUNCATE TABLE equipo CASCADE;
TRUNCATE TABLE aula CASCADE;
TRUNCATE TABLE edificio CASCADE;
TRUNCATE TABLE administrador CASCADE;
TRUNCATE TABLE apoyo_logistica CASCADE;
TRUNCATE TABLE usuario CASCADE;
TRUNCATE TABLE config_sistema CASCADE;

-- 2. Configuración Inicial del Sistema (ConfigSistema)
-- Solo se maneja un registro global. margen_encendido = 15 min, tiempo_minimo_desperdicio = 30 min.
INSERT INTO config_sistema (id, margen_encendido, tiempo_minimo_desperdicio, activo)
VALUES ('7b1f5e8c-3d2a-4f9e-8c6b-1a2b3c4d5e6f', 15, 30, true);

-- 3. Usuarios de Sistema (joined inheritance: Usuario + Administrador / ApoyoLogistica)
-- Administrador: admin / admin123
INSERT INTO usuario (id, activo, nombre, apellido, cif, password)
VALUES ('a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d', true, 'Carlos', 'Mendoza', 'admin', 'admin123');
INSERT INTO administrador (id, nivel_acceso)
VALUES ('a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d', 'SuperAdministrador');

-- Personal de Apoyo Logístico 1: log1 / log123
INSERT INTO usuario (id, activo, nombre, apellido, cif, password)
VALUES ('b2c3d4e5-f6a7-8b9c-0d1e-2f3a4b5c6d7e', true, 'Juan', 'Pérez', 'log1', 'log123');
INSERT INTO apoyo_logistica (id)
VALUES ('b2c3d4e5-f6a7-8b9c-0d1e-2f3a4b5c6d7e');

-- Personal de Apoyo Logístico 2: log2 / log123
INSERT INTO usuario (id, activo, nombre, apellido, cif, password)
VALUES ('c3d4e5f6-a7b8-9c0d-1e2f-3a4b5c6d7e8f', true, 'María', 'Gómez', 'log2', 'log123');
INSERT INTO apoyo_logistica (id)
VALUES ('c3d4e5f6-a7b8-9c0d-1e2f-3a4b5c6d7e8f');

-- 4. Edificios
INSERT INTO edificio (id, nombre) VALUES ('e0100000-0000-0000-0000-000000000001', 'Edificio A');
INSERT INTO edificio (id, nombre) VALUES ('e0100000-0000-0000-0000-000000000002', 'Edificio B');
INSERT INTO edificio (id, nombre) VALUES ('e0100000-0000-0000-0000-000000000003', 'Edificio C');
INSERT INTO edificio (id, nombre) VALUES ('e0100000-0000-0000-0000-000000000004', 'Edificio D');
INSERT INTO edificio (id, nombre) VALUES ('e0100000-0000-0000-0000-000000000005', 'Edificio M');
INSERT INTO edificio (id, nombre) VALUES ('e0100000-0000-0000-0000-000000000006', 'Edificio P');

-- 5. Aulas
-- Edificio A
INSERT INTO aula (id, codigo, edificio_id) VALUES ('a0200000-0000-0000-0000-000000000011', 'A-101', 'e0100000-0000-0000-0000-000000000001');
INSERT INTO aula (id, codigo, edificio_id) VALUES ('a0200000-0000-0000-0000-000000000012', 'A-102', 'e0100000-0000-0000-0000-000000000001');
INSERT INTO aula (id, codigo, edificio_id) VALUES ('a0200000-0000-0000-0000-000000000013', 'A-103', 'e0100000-0000-0000-0000-000000000001');
INSERT INTO aula (id, codigo, edificio_id) VALUES ('a0200000-0000-0000-0000-000000000014', 'A-104', 'e0100000-0000-0000-0000-000000000001'); -- Sin equipo

-- Edificio B
INSERT INTO aula (id, codigo, edificio_id) VALUES ('a0200000-0000-0000-0000-000000000021', 'B-201', 'e0100000-0000-0000-0000-000000000002');
INSERT INTO aula (id, codigo, edificio_id) VALUES ('a0200000-0000-0000-0000-000000000022', 'B-202', 'e0100000-0000-0000-0000-000000000002');
INSERT INTO aula (id, codigo, edificio_id) VALUES ('a0200000-0000-0000-0000-000000000023', 'B-208', 'e0100000-0000-0000-0000-000000000002'); -- Sin equipo

-- Edificio C
INSERT INTO aula (id, codigo, edificio_id) VALUES ('a0200000-0000-0000-0000-000000000031', 'C-201', 'e0100000-0000-0000-0000-000000000003');
INSERT INTO aula (id, codigo, edificio_id) VALUES ('a0200000-0000-0000-0000-000000000032', 'C-202', 'e0100000-0000-0000-0000-000000000003');
INSERT INTO aula (id, codigo, edificio_id) VALUES ('a0200000-0000-0000-0000-000000000033', 'C-209', 'e0100000-0000-0000-0000-000000000003'); -- Sin equipo

-- Edificio D
INSERT INTO aula (id, codigo, edificio_id) VALUES ('a0200000-0000-0000-0000-000000000041', 'D-301', 'e0100000-0000-0000-0000-000000000004');
INSERT INTO aula (id, codigo, edificio_id) VALUES ('a0200000-0000-0000-0000-000000000042', 'D-302', 'e0100000-0000-0000-0000-000000000004'); -- Sin equipo

-- Edificio M
INSERT INTO aula (id, codigo, edificio_id) VALUES ('a0200000-0000-0000-0000-000000000051', 'M-110', 'e0100000-0000-0000-0000-000000000005');
INSERT INTO aula (id, codigo, edificio_id) VALUES ('a0200000-0000-0000-0000-000000000052', 'M-111', 'e0100000-0000-0000-0000-000000000005'); -- Sin equipo

-- Edificio P
INSERT INTO aula (id, codigo, edificio_id) VALUES ('a0200000-0000-0000-0000-000000000061', 'P-101', 'e0100000-0000-0000-0000-000000000006');
INSERT INTO aula (id, codigo, edificio_id) VALUES ('a0200000-0000-0000-0000-000000000062', 'P-102', 'e0100000-0000-0000-0000-000000000006'); -- Sin equipo

-- 6. Equipos de Aire Acondicionado
-- Aulas con equipo asignado (coherencia de potencia en Watts)
INSERT INTO equipo (id, marca, modelo, btu, eficiencia, operativo, potencia_minima, potencia_nominal, estado, aula_id)
VALUES ('d0300000-0000-0000-0000-000000000011', 'Samsung', 'AR18Inverter', 18000, 'A++', true, 400.0, 1600.0, 'ENCENDIDO', 'a0200000-0000-0000-0000-000000000011');

-- A-102: Apagado, servirá para activar "Falta Climatización" dinámicamente en 2 minutos.
INSERT INTO equipo (id, marca, modelo, btu, eficiencia, operativo, potencia_minima, potencia_nominal, estado, aula_id)
VALUES ('d0300000-0000-0000-0000-000000000012', 'LG', 'DualInverter12', 12000, 'A+++', true, 300.0, 1200.0, 'APAGADO', 'a0200000-0000-0000-0000-000000000012');

INSERT INTO equipo (id, marca, modelo, btu, eficiencia, operativo, potencia_minima, potencia_nominal, estado, aula_id)
VALUES ('d0300000-0000-0000-0000-000000000013', 'Daikin', 'FTKM24', 24000, 'A+++', true, 600.0, 2400.0, 'ENCENDIDO', 'a0200000-0000-0000-0000-000000000013');

INSERT INTO equipo (id, marca, modelo, btu, eficiencia, operativo, potencia_minima, potencia_nominal, estado, aula_id)
VALUES ('d0300000-0000-0000-0000-000000000021', 'Carrier', 'Comfort18', 18000, 'A', true, 500.0, 1900.0, 'ENCENDIDO', 'a0200000-0000-0000-0000-000000000021');

-- B-202: Encendido, servirá para activar "Desperdicio" dinámicamente cuando su clase acabe en 2 minutos.
INSERT INTO equipo (id, marca, modelo, btu, eficiencia, operativo, potencia_minima, potencia_nominal, estado, aula_id)
VALUES ('d0300000-0000-0000-0000-000000000022', 'Midea', 'Mission12', 12000, 'A++', true, 350.0, 1300.0, 'ENCENDIDO', 'a0200000-0000-0000-0000-000000000022');

INSERT INTO equipo (id, marca, modelo, btu, eficiencia, operativo, potencia_minima, potencia_nominal, estado, aula_id)
VALUES ('d0300000-0000-0000-0000-000000000031', 'Samsung', 'AR24Inverter', 24000, 'A+++', true, 550.0, 2450.0, 'ENCENDIDO', 'a0200000-0000-0000-0000-000000000031');

INSERT INTO equipo (id, marca, modelo, btu, eficiencia, operativo, potencia_minima, potencia_nominal, estado, aula_id)
VALUES ('d0300000-0000-0000-0000-000000000032', 'LG', 'ArtCool18', 18000, 'A++', true, 450.0, 1800.0, 'APAGADO', 'a0200000-0000-0000-0000-000000000032');

INSERT INTO equipo (id, marca, modelo, btu, eficiencia, operativo, potencia_minima, potencia_nominal, estado, aula_id)
VALUES ('d0300000-0000-0000-0000-000000000041', 'Daikin', 'FTKM18', 18000, 'A+++', true, 450.0, 1800.0, 'APAGADO', 'a0200000-0000-0000-0000-000000000041');

INSERT INTO equipo (id, marca, modelo, btu, eficiencia, operativo, potencia_minima, potencia_nominal, estado, aula_id)
VALUES ('d0300000-0000-0000-0000-000000000051', 'Carrier', 'Titan36', 36000, 'A', true, 900.0, 3500.0, 'ENCENDIDO', 'a0200000-0000-0000-0000-000000000051');

INSERT INTO equipo (id, marca, modelo, btu, eficiencia, operativo, potencia_minima, potencia_nominal, estado, aula_id)
VALUES ('d0300000-0000-0000-0000-000000000061', 'Samsung', 'WindFree12', 12000, 'A+++', true, 300.0, 1200.0, 'ENCENDIDO', 'a0200000-0000-0000-0000-000000000061');

-- Equipos plantilla sin asignar (para simular el dropdown y asociarlos en el modulo de asignaciones)
INSERT INTO equipo (id, marca, modelo, btu, eficiencia, operativo, potencia_minima, potencia_nominal, estado, aula_id)
VALUES ('d0300000-0000-0000-0000-000000000901', 'Samsung', 'Modelo Base 12K W', 12000, 'A+++', true, 300.0, 1200.0, 'APAGADO', NULL);

INSERT INTO equipo (id, marca, modelo, btu, eficiencia, operativo, potencia_minima, potencia_nominal, estado, aula_id)
VALUES ('d0300000-0000-0000-0000-000000000902', 'LG', 'Modelo Base 18K W', 18000, 'A++', true, 450.0, 1800.0, 'APAGADO', NULL);

INSERT INTO equipo (id, marca, modelo, btu, eficiencia, operativo, potencia_minima, potencia_nominal, estado, aula_id)
VALUES ('d0300000-0000-0000-0000-000000000903', 'Daikin', 'Modelo Base 24K W', 24000, 'A+++', true, 600.0, 2400.0, 'APAGADO', NULL);

INSERT INTO equipo (id, marca, modelo, btu, eficiencia, operativo, potencia_minima, potencia_nominal, estado, aula_id)
VALUES ('d0300000-0000-0000-0000-000000000904', 'Carrier', 'Modelo Base 36K W', 36000, 'A', true, 900.0, 3500.0, 'APAGADO', NULL);


-- 7. Horarios Académicos Dinámicos (Ocurren HOY)
-- EXTRACT(ISODOW FROM CURRENT_DATE) asocia los horarios al día de la semana actual.

-- Aula A-101: Clase en curso actualmente (empezó hace 1 hora, termina en 1 hora)
INSERT INTO horario_academico (id, asignatura, dia_semana, hora_inicio, hora_fin, aula_id)
VALUES ('f0400000-0000-0000-0000-000000000011', 'Matemáticas I', EXTRACT(ISODOW FROM CURRENT_DATE), (CURRENT_TIME - INTERVAL '1 hour')::time, (CURRENT_TIME + INTERVAL '1 hour')::time, 'a0200000-0000-0000-0000-000000000011');

-- Aula B-201: Clase en curso actualmente (empezó hace 30 min, termina en 1 hora y media)
INSERT INTO horario_academico (id, asignatura, dia_semana, hora_inicio, hora_fin, aula_id)
VALUES ('f0400000-0000-0000-0000-000000000021', 'Física II', EXTRACT(ISODOW FROM CURRENT_DATE), (CURRENT_TIME - INTERVAL '30 minutes')::time, (CURRENT_TIME + INTERVAL '90 minutes')::time, 'a0200000-0000-0000-0000-000000000021');

-- Aula C-201: Clase en curso actualmente (empezó hace 45 min, termina en 30 min)
INSERT INTO horario_academico (id, asignatura, dia_semana, hora_inicio, hora_fin, aula_id)
VALUES ('f0400000-0000-0000-0000-000000000031', 'Programación Avanzada', EXTRACT(ISODOW FROM CURRENT_DATE), (CURRENT_TIME - INTERVAL '45 minutes')::time, (CURRENT_TIME + INTERVAL '30 minutes')::time, 'a0200000-0000-0000-0000-000000000031');

-- Aula A-102: Clase próxima a iniciar (Empieza en 17 minutos). 
-- Generará alerta "Falta Climatización" a los 2 minutos, cuando el tiempo faltante sea <= 15 minutos.
INSERT INTO horario_academico (id, asignatura, dia_semana, hora_inicio, hora_fin, aula_id)
VALUES ('f0400000-0000-0000-0000-000000000012', 'Química Orgánica', EXTRACT(ISODOW FROM CURRENT_DATE), (CURRENT_TIME + INTERVAL '17 minutes')::time, (CURRENT_TIME + INTERVAL '137 minutes')::time, 'a0200000-0000-0000-0000-000000000012');

-- Aula B-202: 
-- Clase anterior acaba en 2 minutos (empezó hace 88 minutos).
-- Clase siguiente empieza en 42 minutos.
-- Generará alerta "Desperdicio" a los 2 minutos porque el equipo sigue encendido durante un espacio sin clases de 40 minutos (>= 30 min desperdicio).
INSERT INTO horario_academico (id, asignatura, dia_semana, hora_inicio, hora_fin, aula_id)
VALUES ('f0400000-0000-0000-0000-000000000022', 'Cálculo Vectorial I', EXTRACT(ISODOW FROM CURRENT_DATE), (CURRENT_TIME - INTERVAL '88 minutes')::time, (CURRENT_TIME + INTERVAL '2 minutes')::time, 'a0200000-0000-0000-0000-000000000022');
INSERT INTO horario_academico (id, asignatura, dia_semana, hora_inicio, hora_fin, aula_id)
VALUES ('f0400000-0000-0000-0000-000000000023', 'Cálculo Vectorial II', EXTRACT(ISODOW FROM CURRENT_DATE), (CURRENT_TIME + INTERVAL '42 minutes')::time, (CURRENT_TIME + INTERVAL '162 minutes')::time, 'a0200000-0000-0000-0000-000000000022');

-- Aula A-103: Clase finalizada (terminó hace 2 horas, duró 2 horas)
INSERT INTO horario_academico (id, asignatura, dia_semana, hora_inicio, hora_fin, aula_id)
VALUES ('f0400000-0000-0000-0000-000000000013', 'Álgebra Lineal', EXTRACT(ISODOW FROM CURRENT_DATE), (CURRENT_TIME - INTERVAL '4 hours')::time, (CURRENT_TIME - INTERVAL '2 hours')::time, 'a0200000-0000-0000-0000-000000000013');

-- Aula C-202: Clase futura lejana (empieza en 3 horas)
INSERT INTO horario_academico (id, asignatura, dia_semana, hora_inicio, hora_fin, aula_id)
VALUES ('f0400000-0000-0000-0000-000000000032', 'Estructuras de Datos', EXTRACT(ISODOW FROM CURRENT_DATE), (CURRENT_TIME + INTERVAL '3 hours')::time, (CURRENT_TIME + INTERVAL '5 hours')::time, 'a0200000-0000-0000-0000-000000000032');

-- Aula M-110: Clase en curso (empezó hace 3 horas, termina en 1 hora)
INSERT INTO horario_academico (id, asignatura, dia_semana, hora_inicio, hora_fin, aula_id)
VALUES ('f0400000-0000-0000-0000-000000000051', 'Diseño de Sistemas', EXTRACT(ISODOW FROM CURRENT_DATE), (CURRENT_TIME - INTERVAL '3 hours')::time, (CURRENT_TIME + INTERVAL '1 hour')::time, 'a0200000-0000-0000-0000-000000000051');

-- Aula P-101: Clase en curso (empezó hace 2 horas, termina en 1 hora)
INSERT INTO horario_academico (id, asignatura, dia_semana, hora_inicio, hora_fin, aula_id)
VALUES ('f0400000-0000-0000-0000-000000000061', 'Física Moderna', EXTRACT(ISODOW FROM CURRENT_DATE), (CURRENT_TIME - INTERVAL '2 hours')::time, (CURRENT_TIME + INTERVAL '1 hour')::time, 'a0200000-0000-0000-0000-000000000061');


-- 8. Registros Operativos (Históricos y Activos)

-- ====================
-- REGISTROS HISTÓRICOS CERRADOS (Para reportes y gráficos históricos de los últimos 7 días)
-- ====================

-- Hace 7 días: Aula A-101 encendido de 08:00 a 12:00 (4 horas). Consumo = (1000W * 4h)/1000 = 4.0 kWh
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id)
VALUES ('f0500000-0000-0000-0000-000000000071', 'ENCENDIDO', 4.0, CURRENT_DATE - INTERVAL '7 days' + TIME '08:00:00', CURRENT_DATE - INTERVAL '7 days' + TIME '12:00:00', 'd0300000-0000-0000-0000-000000000011');
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id)
VALUES ('f0500000-0000-0000-0000-000000000072', 'APAGADO', 0.0, CURRENT_DATE - INTERVAL '7 days' + TIME '12:00:00', CURRENT_DATE - INTERVAL '7 days' + TIME '20:00:00', 'd0300000-0000-0000-0000-000000000011');

-- Hace 6 días: Aula A-101 encendido de 08:00 a 11:00 (3 horas). Consumo = (1000W * 3h)/1000 = 3.0 kWh
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id)
VALUES ('f0500000-0000-0000-0000-000000000061', 'ENCENDIDO', 3.0, CURRENT_DATE - INTERVAL '6 days' + TIME '08:00:00', CURRENT_DATE - INTERVAL '6 days' + TIME '11:00:00', 'd0300000-0000-0000-0000-000000000011');
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id)
VALUES ('f0500000-0000-0000-0000-000000000062', 'APAGADO', 0.0, CURRENT_DATE - INTERVAL '6 days' + TIME '11:00:00', CURRENT_DATE - INTERVAL '6 days' + TIME '20:00:00', 'd0300000-0000-0000-0000-000000000011');

-- Hace 5 días: Aula B-201 encendido de 09:00 a 13:00 (4 horas). Consumo = (1200W * 4h)/1000 = 4.8 kWh
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id)
VALUES ('f0500000-0000-0000-0000-000000000051', 'ENCENDIDO', 4.8, CURRENT_DATE - INTERVAL '5 days' + TIME '09:00:00', CURRENT_DATE - INTERVAL '5 days' + TIME '13:00:00', 'd0300000-0000-0000-0000-000000000021');

-- Hace 4 días: Aula C-201 encendido de 07:00 a 10:00 (3 horas). Consumo = (1500W * 3h)/1000 = 4.5 kWh
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id)
VALUES ('f0500000-0000-0000-0000-000000000041', 'ENCENDIDO', 4.5, CURRENT_DATE - INTERVAL '4 days' + TIME '07:00:00', CURRENT_DATE - INTERVAL '4 days' + TIME '10:00:00', 'd0300000-0000-0000-0000-000000000031');

-- Hace 3 días: Aula M-110 encendido de 10:00 a 15:00 (5 horas). Consumo = (2200W * 5h)/1000 = 11.0 kWh
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id)
VALUES ('f0500000-0000-0000-0000-000000000031', 'ENCENDIDO', 11.0, CURRENT_DATE - INTERVAL '3 days' + TIME '10:00:00', CURRENT_DATE - INTERVAL '3 days' + TIME '15:00:00', 'd0300000-0000-0000-0000-000000000051');

-- Hace 2 días: Aula P-101 encendido de 11:00 a 14:00 (3 horas). Consumo = (750W * 3h)/1000 = 2.25 kWh
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id)
VALUES ('f0500000-0000-0000-0000-000000000021', 'ENCENDIDO', 2.25, CURRENT_DATE - INTERVAL '2 days' + TIME '11:00:00', CURRENT_DATE - INTERVAL '2 days' + TIME '14:00:00', 'd0300000-0000-0000-0000-000000000061');

-- Hace 1 día (Ayer): Varios consumos combinados
-- A-101: 2 turnos de 3 horas. Consumo: 6.0 kWh
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id)
VALUES ('f0500000-0000-0000-0000-000000000101', 'ENCENDIDO', 3.0, CURRENT_DATE - INTERVAL '1 day' + TIME '08:00:00', CURRENT_DATE - INTERVAL '1 day' + TIME '11:00:00', 'd0300000-0000-0000-0000-000000000011');
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id)
VALUES ('f0500000-0000-0000-0000-000000000102', 'ENCENDIDO', 3.0, CURRENT_DATE - INTERVAL '1 day' + TIME '14:00:00', CURRENT_DATE - INTERVAL '1 day' + TIME '17:00:00', 'd0300000-0000-0000-0000-000000000011');
-- B-201: 1 turno de 4 horas. Consumo: 4.8 kWh
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id)
VALUES ('f0500000-0000-0000-0000-000000000103', 'ENCENDIDO', 4.8, CURRENT_DATE - INTERVAL '1 day' + TIME '09:00:00', CURRENT_DATE - INTERVAL '1 day' + TIME '13:00:00', 'd0300000-0000-0000-0000-000000000021');
-- C-201: 1 turno de 5 horas. Consumo: 7.5 kWh
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id)
VALUES ('f0500000-0000-0000-0000-000000000104', 'ENCENDIDO', 7.5, CURRENT_DATE - INTERVAL '1 day' + TIME '08:00:00', CURRENT_DATE - INTERVAL '1 day' + TIME '13:00:00', 'd0300000-0000-0000-0000-000000000031');


-- ==========================================
-- REGISTROS ACTIVOS / ABIERTOS (Para simular estados actuales y AHORRO POSITIVO hoy)
-- ==========================================

-- Aula A-101 (Promedio 1.0 kW): Clase empezó hace 1h (esperado = 1.0 kWh).
-- AC encendido hace 2 horas (consumo real = 2.0 kWh). [Desperdicio de 1.0 kWh]
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id)
VALUES ('f0500000-0000-0000-0000-000000000991', 'ENCENDIDO', 0.0, CURRENT_DATE + (CURRENT_TIME - INTERVAL '2 hours'), NULL, 'd0300000-0000-0000-0000-000000000011');

-- Aula A-102 (Promedio 0.75 kW): Apagado. Clase empieza en 17 minutos (esperado = 0.0 kWh).
-- AC Apagado (consumo real = 0.0 kWh).
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id)
VALUES ('f0500000-0000-0000-0000-000000000997', 'APAGADO', 0.0, CURRENT_DATE + (CURRENT_TIME - INTERVAL '3 hours'), NULL, 'd0300000-0000-0000-0000-000000000012');

-- Aula A-103 (Promedio 1.5 kW): Clase finalizada hace 2 horas, duró 2 horas (esperado = 3.0 kWh).
-- AC sigue encendido hace 4 horas (consumo real = 6.0 kWh). [Desperdicio de 3.0 kWh]
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id)
VALUES ('f0500000-0000-0000-0000-000000000996', 'ENCENDIDO', 0.0, CURRENT_DATE + (CURRENT_TIME - INTERVAL '4 hours'), NULL, 'd0300000-0000-0000-0000-000000000013');

-- Aula B-201 (Promedio 1.2 kW): Clase empezó hace 30 min (esperado = 0.6 kWh).
-- AC se encendió tarde, hace solo 10 minutos (consumo real = 0.2 kWh). [Ahorro de 0.4 kWh]
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id)
VALUES ('f0500000-0000-0000-0000-000000000992', 'ENCENDIDO', 0.0, CURRENT_DATE + (CURRENT_TIME - INTERVAL '10 minutes'), NULL, 'd0300000-0000-0000-0000-000000000021');

-- Aula B-202 (Promedio 0.825 kW): Clase acaba en 2 minutos (duración transcurrida 88 min, esperado = 1.21 kWh).
-- AC encendido durante toda la clase (consumo real = 1.65 kWh ya que arrancó hace 2 horas). [Desperdicio de 0.44 kWh]
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id)
VALUES ('f0500000-0000-0000-0000-000000000998', 'ENCENDIDO', 0.0, CURRENT_DATE + (CURRENT_TIME - INTERVAL '2 hours'), NULL, 'd0300000-0000-0000-0000-000000000022');

-- Aula C-201 (Promedio 1.5 kW): Clase empezó hace 45 min (esperado = 1.125 kWh).
-- AC se encendió tarde, hace solo 15 minutos (consumo real = 0.375 kWh). [Ahorro de 0.75 kWh]
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id)
VALUES ('f0500000-0000-0000-0000-000000000993', 'ENCENDIDO', 0.0, CURRENT_DATE + (CURRENT_TIME - INTERVAL '15 minutes'), NULL, 'd0300000-0000-0000-0000-000000000031');

-- Aula C-202: Apagado. Clase futura lejana en 3 horas (esperado = 0.0 kWh).
-- AC Apagado (consumo real = 0.0 kWh).
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id)
VALUES ('f0500000-0000-0000-0000-000000000999', 'APAGADO', 0.0, CURRENT_DATE + (CURRENT_TIME - INTERVAL '5 hours'), NULL, 'd0300000-0000-0000-0000-000000000032');

-- Aula D-301: Apagado. Sin clase (esperado = 0.0 kWh).
-- AC Apagado (consumo real = 0.0 kWh).
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id)
VALUES ('f0500000-0000-0000-0000-000000000989', 'APAGADO', 0.0, CURRENT_DATE + (CURRENT_TIME - INTERVAL '5 hours'), NULL, 'd0300000-0000-0000-0000-000000000041');

-- Aula M-110 (Promedio 2.2 kW): Clase empezó hace 3 horas (esperado = 6.6 kWh).
-- AC encendido tarde, hace solo 1 hora (consumo real = 2.2 kWh). [Ahorro de 4.4 kWh]
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id)
VALUES ('f0500000-0000-0000-0000-000000000994', 'ENCENDIDO', 0.0, CURRENT_DATE + (CURRENT_TIME - INTERVAL '1 hour'), NULL, 'd0300000-0000-0000-0000-000000000051');

-- Aula P-101 (Promedio 0.75 kW): Clase empezó hace 2 horas (esperado = 1.5 kWh).
-- AC encendido tarde, hace solo 30 min (consumo real = 0.375 kWh). [Ahorro de 1.125 kWh]
INSERT INTO registro_operativo (id, estado, consumo, inicio, fin, equipo_id)
VALUES ('f0500000-0000-0000-0000-000000000995', 'ENCENDIDO', 0.0, CURRENT_DATE + (CURRENT_TIME - INTERVAL '30 minutes'), NULL, 'd0300000-0000-0000-0000-000000000061');

-- BALANCE DE AHORROS HOY:
-- Esperado acumulado total = 1.0 (A-101) + 0.0 (A-102) + 3.0 (A-103) + 0.6 (B-201) + 1.21 (B-202) + 1.125 (C-201) + 6.6 (M-110) + 1.5 (P-101) = 15.035 kWh.
-- Real acumulado total     = 2.0 (A-101) + 0.0 (A-102) + 6.0 (A-103) + 0.2 (B-201) + 1.65 (B-202) + 0.375 (C-201) + 2.2 (M-110) + 0.375 (P-101) = 12.8 kWh.
-- Ahorro logrado hoy       = (15.035 - 12.8) / 15.035 = 14.86% de ahorro positivo!


-- 9. Alertas de Prueba (Iniciales ya existentes para visualizar de inmediato)
-- Alerta 1: Desperdicio Energético pendiente (Aula A-103, el AC sigue encendido tras 2 horas de haber terminado la clase)
INSERT INTO alerta (id, tipo_alerta, estado, fecha_hora, aula_id)
VALUES ('f0600000-0000-0000-0000-000000000001', 'Desperdicio Energético', 'PENDIENTE', CURRENT_TIMESTAMP - INTERVAL '30 minutes', 'a0200000-0000-0000-0000-000000000013');

-- Alerta 2: Falta Climatización pendiente (Aula B-201, tiene clase en curso pero estuvo apagado inicialmente)
INSERT INTO alerta (id, tipo_alerta, estado, fecha_hora, aula_id)
VALUES ('f0600000-0000-0000-0000-000000000002', 'Falta Climatización', 'PENDIENTE', CURRENT_TIMESTAMP - INTERVAL '15 minutes', 'a0200000-0000-0000-0000-000000000021');

-- Alerta 3: Desperdicio Energético atendido (Aula C-201, resuelta)
INSERT INTO alerta (id, tipo_alerta, estado, fecha_hora, aula_id)
VALUES ('f0600000-0000-0000-0000-000000000003', 'Desperdicio Energético', 'ATENDIDA', CURRENT_TIMESTAMP - INTERVAL '3 hours', 'a0200000-0000-0000-0000-000000000031');

-- Alerta 4: Falta Climatización atendida (Aula A-101, resuelta)
INSERT INTO alerta (id, tipo_alerta, estado, fecha_hora, aula_id)
VALUES ('f0600000-0000-0000-0000-000000000004', 'Falta Climatización', 'ATENDIDA', CURRENT_TIMESTAMP - INTERVAL '5 hours', 'a0200000-0000-0000-0000-000000000011');


-- 10. Incidencias
-- Incidencia 1: Fallo eléctrico en el Edificio A (Pendiente)
INSERT INTO incidencia (id, descripcion, fecha_hora, tipo_incidencia, estado, aula_id)
VALUES ('f0700000-0000-0000-0000-000000000001', 'El disyuntor principal salta continuamente al intentar arrancar el aire acondicionado.', CURRENT_TIMESTAMP - INTERVAL '1 day', 'Fallo eléctrico', 'PENDIENTE', 'a0200000-0000-0000-0000-000000000011');

-- Incidencia 2: Limpieza de filtro requerida (Pendiente)
INSERT INTO incidencia (id, descripcion, fecha_hora, tipo_incidencia, estado, aula_id)
VALUES ('f0700000-0000-0000-0000-000000000002', 'El equipo emite un silbido persistente y la ventilación es débil. Posible filtro obstruido.', CURRENT_TIMESTAMP - INTERVAL '4 hours', 'Problema de climatización', 'PENDIENTE', 'a0200000-0000-0000-0000-000000000021');

-- Incidencia 3: Mantenimiento regular finalizado (Atendida)
INSERT INTO incidencia (id, descripcion, fecha_hora, tipo_incidencia, estado, aula_id)
VALUES ('f0700000-0000-0000-0000-000000000003', 'Carga de gas refrigerante R410A realizada correctamente tras detección de fuga.', CURRENT_TIMESTAMP - INTERVAL '3 days', 'Mantenimiento', 'ATENDIDA', 'a0200000-0000-0000-0000-000000000031');


-- 11. Asignaciones de Edificios para Personal Logístico
-- Juan Pérez (log1) asignado a Edificio A y Edificio B
INSERT INTO asignacion_edificio (id, activo, fecha_asignacion, logistica_id, edificio_id)
VALUES ('9a8b7c6d-5e4f-3a2b-1c0d-9e8f7a6b5c4d', true, CURRENT_DATE - INTERVAL '10 days', 'b2c3d4e5-f6a7-8b9c-0d1e-2f3a4b5c6d7e', 'e0100000-0000-0000-0000-000000000001');

INSERT INTO asignacion_edificio (id, activo, fecha_asignacion, logistica_id, edificio_id)
VALUES ('8b7c6d5e-4f3a-2b1c-0d9e-8f7a6b5c4d3e', true, CURRENT_DATE - INTERVAL '10 days', 'b2c3d4e5-f6a7-8b9c-0d1e-2f3a4b5c6d7e', 'e0100000-0000-0000-0000-000000000002');

-- María Gómez (log2) asignado a Edificio C y Edificio P
INSERT INTO asignacion_edificio (id, activo, fecha_asignacion, logistica_id, edificio_id)
VALUES ('7c6d5e4f-3a2b-1c0d-9e8f-7a6b5c4d3e2f', true, CURRENT_DATE - INTERVAL '10 days', 'c3d4e5f6-a7b8-9c0d-1e2f-3a4b5c6d7e8f', 'e0100000-0000-0000-0000-000000000003');

INSERT INTO asignacion_edificio (id, activo, fecha_asignacion, logistica_id, edificio_id)
VALUES ('6d5e4f3a-2b1c-0d9e-8f7a-6b5c4d3e2f1a', true, CURRENT_DATE - INTERVAL '10 days', 'c3d4e5f6-a7b8-9c0d-1e2f-3a4b5c6d7e8f', 'e0100000-0000-0000-0000-000000000006');
