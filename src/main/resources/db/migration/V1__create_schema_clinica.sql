
-- ===============================
-- TABLA PACIENTE
-- ===============================
CREATE  TABLE paciente (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           nombres VARCHAR(50) NOT NULL,
                           apellidos VARCHAR(50) NOT NULL,
                           fecha_nacimiento DATE NOT NULL,
                           dni CHAR(8) NOT NULL UNIQUE,
                           direccion VARCHAR(100),
                           telefono VARCHAR(15),
                           email VARCHAR(100) UNIQUE,
                           fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
                           usuario_creacion VARCHAR(50),
                           fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           usuario_actualizacion VARCHAR(50)
);


-- ===============================
-- TABLA ESPECIALIDAD
-- ===============================
CREATE TABLE especialidad (
                              id INT PRIMARY KEY AUTO_INCREMENT,
                              nombre VARCHAR(50) NOT NULL UNIQUE,
                              descripcion VARCHAR(100),
                              fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
                              usuario_creacion VARCHAR(50),
                              fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              usuario_actualizacion VARCHAR(50)
);



-- ===============================
-- TABLA MEDICO
-- ===============================
CREATE TABLE medico (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        nombres VARCHAR(50) NOT NULL,
                        apellidos VARCHAR(50) NOT NULL,
                        cmp VARCHAR(10) NOT NULL UNIQUE,
                        id_especialidad INT NOT NULL,
                        FOREIGN KEY(id_Especialidad) REFERENCES especialidad(id)
                            ON DELETE RESTRICT ON UPDATE CASCADE,
                        fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
                        usuario_creacion VARCHAR(50),
                        fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        usuario_actualizacion VARCHAR(50)
);


-- ===============================
-- TABLA USUARIO
-- ===============================
CREATE TABLE usuario (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         username VARCHAR(50) NOT NULL UNIQUE,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         password_hash VARCHAR(255) NOT NULL,
                         nombre VARCHAR(100),
                         apellido VARCHAR(100),
                         activo BOOLEAN NOT NULL DEFAULT TRUE
);

-- ===============================
-- TABLA ROLES
-- ===============================
CREATE TABLE roles (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       nombre VARCHAR(50) NOT NULL UNIQUE,
                       descripcion VARCHAR(255),
                       activo BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabla intermedia explícita con su propio id

CREATE TABLE usuario_rol (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             usuario_id BIGINT NOT NULL,
                             rol_id BIGINT NOT NULL,
                             activo BOOLEAN NOT NULL DEFAULT TRUE,
                             FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE,
                             FOREIGN KEY (rol_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- ===============================
-- TABLA HORARIO
-- ===============================
CREATE TABLE horario (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         dia ENUM('Lunes','Martes','Miercoles','Jueves','Viernes','Sabado','Domingo') NOT NULL,
                         horaInicio TIME NOT NULL,
                         horaFin TIME NOT NULL,
                         idMedico INT NOT NULL,
                         FOREIGN KEY(idMedico) REFERENCES medico(id)
                             ON DELETE CASCADE ON UPDATE CASCADE,
                         fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
                         usuario_creacion VARCHAR(50),
                         fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         usuario_actualizacion VARCHAR(50)
);

-- ===============================
-- TABLA CITA MEDICA
-- ===============================
CREATE TABLE citamedica (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            fecha DATE NOT NULL,
                            hora TIME NOT NULL,
                            id_Paciente INT NOT NULL,
                            id_Medico INT NOT NULL,
                            motivo VARCHAR(255),
                            estado ENUM('PENDIENTE','CONFIRMADA','CANCELADA','ATENDIDA') DEFAULT 'PENDIENTE',
                            FOREIGN KEY(id_Paciente) REFERENCES paciente(id)
                                ON DELETE CASCADE ON UPDATE CASCADE,
                            FOREIGN KEY(id_Medico) REFERENCES medico(id)
                                ON DELETE CASCADE ON UPDATE CASCADE,
                            fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
                            usuario_creacion VARCHAR(50),
                            fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            usuario_actualizacion VARCHAR(50)
);

-- ===============================
-- TABLA HISTORIA CLINICA
-- ===============================
CREATE TABLE historiaclinica (
                                 id INT PRIMARY KEY AUTO_INCREMENT,
                                 idPaciente INT NOT NULL,
                                 fecha DATE NOT NULL,
                                 diagnostico VARCHAR(255),
                                 tratamiento VARCHAR(255),
                                 observaciones TEXT,
                                 FOREIGN KEY(idPaciente) REFERENCES paciente(id)
                                     ON DELETE CASCADE ON UPDATE CASCADE,
                                 fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
                                 usuario_creacion VARCHAR(50),
                                 fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 usuario_actualizacion VARCHAR(50)
);

-- ===============================
-- TABLA PROVEEDOR
-- ===============================
CREATE TABLE proveedor (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           nombre VARCHAR(100) NOT NULL,
                           ruc CHAR(11) NOT NULL UNIQUE,
                           direccion VARCHAR(100),
                           telefono VARCHAR(15),
                           email VARCHAR(100) UNIQUE,
                           fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
                           usuario_creacion VARCHAR(50),
                           fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           usuario_actualizacion VARCHAR(50)
);
-- ===============================
-- TABLA MEDICAMENTO
-- ===============================
CREATE TABLE medicamento (
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             nombre VARCHAR(100) NOT NULL UNIQUE,
                             descripcion VARCHAR(255),
                             stockActual INT DEFAULT 0,
                             stockMinimo INT DEFAULT 0,
                             precio DECIMAL(10,2) NOT NULL,
                             fechaVencimiento DATE,
                             fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
                             usuario_creacion VARCHAR(50),
                             fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             usuario_actualizacion VARCHAR(50)
);

-- ===============================
-- TABLA INGRESO MEDICAMENTO
-- ===============================
CREATE TABLE ingreso_medicamento (
                                     id INT PRIMARY KEY AUTO_INCREMENT,
                                     idMedicamento INT NOT NULL,
                                     idProveedor INT NOT NULL,
                                     fechaIngreso DATE NOT NULL,
                                     cantidad INT NOT NULL,
                                     precioCompra DECIMAL(10,2) NOT NULL,
                                     FOREIGN KEY(idMedicamento) REFERENCES medicamento(id)
                                         ON DELETE CASCADE ON UPDATE CASCADE,
                                     FOREIGN KEY(idProveedor) REFERENCES proveedor(id)
                                         ON DELETE RESTRICT ON UPDATE CASCADE,
                                     fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
                                     usuario_creacion VARCHAR(50),
                                     fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                     usuario_actualizacion VARCHAR(50)
);

-- ===============================
-- INDICES ADICIONALES
-- ===============================
CREATE INDEX idx_paciente_dni ON paciente(dni);
CREATE INDEX idx_medico_cmp ON medico(cmp);
CREATE INDEX idx_proveedor_ruc ON proveedor(ruc);
CREATE INDEX idx_usuario_nombre ON usuario(username);

INSERT INTO especialidad (nombre, descripcion, usuario_creacion)
VALUES
    ('Cardiología', 'Especialidad del corazón', 'admin'),
    ('Pediatría', 'Atención médica a niños', 'admin'),
    ('Dermatología', 'Especialidad de la piel', 'admin'),
    ('Neurología', 'Tratamiento del sistema nervioso', 'admin'),
    ('Ginecología', 'Salud reproductiva y atención de la mujer', 'admin'),
    ('Traumatología', 'Diagnóstico y tratamiento de lesiones óseas y musculares', 'admin'),
    ('Ginecología 2', 'Salud reproductiva y atención de la mujer', 'admin');

-- ===============================
-- ROLES
-- ===============================
INSERT INTO roles (nombre, descripcion, activo)
VALUES
    ('ADMIN', 'Acceso total al sistema', TRUE),
    ('RRHH', 'Gestión de personal y nómina', TRUE),
    ('EMPLEADO', 'Acceso básico al sistema', TRUE),
    ('GERENTE', 'Acceso a reportes y gestión avanzada', TRUE),
    ('DOCTOR', 'MEDICOS EN GENERAL', TRUE);;

-- ===============================
-- USUARIOS
-- (contraseñas simuladas como hash: "admin123")
-- ===============================
INSERT INTO usuario (username, email, password_hash, nombre, apellido, activo)
VALUES
    ('admin', 'admin@empresa.com', '$2a$10$neJ49B5F/bS/3BmoAuEOGe/YEUgRroeWGaxJTC.f5n8RJVZhXY2OG', 'Miguel', 'Moreno', TRUE),
    ('rrhh', 'rrhh@empresa.com', '$2a$10$neJ49B5F/bS/3BmoAuEOGe/YEUgRroeWGaxJTC.f5n8RJVZhXY2OG', 'Recursos', 'Humanos', TRUE),
    ('recepcion', 'recepcion@empresa.com', '$2a$10$neJ49B5F/bS/3BmoAuEOGe/YEUgRroeWGaxJTC.f5n8RJVZhXY2OG', 'Recepcion', 'FrontDesk', TRUE),
    ('gerente', 'gerente@empresa.com', '$2a$10$neJ49B5F/bS/3BmoAuEOGe/YEUgRroeWGaxJTC.f5n8RJVZhXY2OG', 'Gerente', 'General', TRUE);

-- tabla intermedia UsuarioRol

INSERT INTO usuario_rol (usuario_id, rol_id, activo)
VALUES
    (1, 1, TRUE), -- admin → ADMIN
    (2, 2, TRUE), -- rrhh → RRHH
    (3, 3, TRUE), -- recepcion → EMPLEADO
    (4, 4, TRUE); -- gerente → GERENTE
-- ===============================
-- PACIENTES
-- ===============================
INSERT INTO paciente (nombres, apellidos, fecha_nacimiento, dni, direccion, telefono, email, usuario_creacion)
VALUES
    ('María', 'Gonzales Pérez', '1990-05-15', '12345678', 'Av. Siempre Viva 123', '987654321', 'maria@example.com', 'admin'),
    ('Carlos', 'Ramírez Soto', '1985-10-22', '87654321', 'Calle Falsa 456', '912345678', 'carlos@example.com', 'admin'),
    ('Lucía', 'Fernández Torres', '2002-03-08', '45678912', 'Jr. Los Olivos 789', '999111222', 'lucia@example.com', 'admin'),
    ('Pedro', 'Lopez Castillo', '1992-07-11', '11223344', 'Av. Grau 321', '988776655', 'pedro.lopez@example.com', 'admin'),
    ('Sofía', 'Mendoza Huamán', '1998-12-25', '22334455', 'Calle San Martín 567', '977665544', 'sofia.mendoza@example.com', 'admin'),
    ('Andrés', 'Paredes Aguilar', '1980-01-30', '33445566', 'Jr. Amazonas 234', '966554433', 'andres.paredes@example.com', 'admin'),
    ('Valeria', 'Torres Lozano', '1995-09-14', '44556677', 'Av. Arequipa 1200', '955443322', 'valeria.torres@example.com', 'admin'),
    ('Miguel', 'Cruz Salazar', '1987-06-19', '55667788', 'Av. Brasil 543', '944332211', 'miguel.cruz@example.com', 'admin'),
    ('Camila', 'Rojas Vera', '1993-11-05', '66778899', 'Jr. Junín 876', '933221100', 'camila.rojas@example.com', 'admin'),
    ('Diego', 'Vásquez Romero', '1999-02-20', '77889900', 'Calle Las Flores 111', '922110099', 'diego.vasquez@example.com', 'admin'),
    ('Elena', 'Navarro Chávez', '1984-08-09', '88990011', 'Av. Universitaria 900', '911009988', 'elena.navarro@example.com', 'admin'),
    ('Raúl', 'Silva Campos', '1991-04-27', '99001122', 'Av. Angamos 450', '900998877', 'raul.silva@example.com', 'admin'),
    ('Patricia', 'Herrera León', '1979-07-02', '10111213', 'Jr. Lampa 654', '989887766', 'patricia.herrera@example.com', 'admin'),
    ('Fernando', 'Gómez Ruiz', '1988-03-18', '12131415', 'Av. Colonial 700', '978776655', 'fernando.gomez@example.com', 'admin'),
    ('Diana', 'Castañeda Morales', '1996-05-23', '14151617', 'Calle Puno 234', '967665544', 'diana.castaneda@example.com', 'admin');


-- ===============================
-- MEDICOS
-- ===============================
INSERT INTO medico (nombres, apellidos, cmp, id_Especialidad, usuario_creacion)
VALUES
    ('José', 'Martínez López', 'CMP1234', 1, 'admin'),
    ('Ana', 'Torres Díaz', 'CMP5678', 2, 'admin'),
    ('Ricardo', 'Flores Vega', 'CMP9012', 3, 'admin'),
    ('María', 'Gonzales Herrera', 'CMP3456', 4, 'admin'),
    ('Luis', 'Ramírez Castro', 'CMP7890', 5, 'admin'),
    ('Carmen', 'Silva Paredes', 'CMP1122', 6, 'admin'),
    ('Jorge', 'Huamán Rojas', 'CMP3344', 1, 'admin'),
    ('Paola', 'Mendoza Quispe', 'CMP5566', 2, 'admin');


-- ===============================
-- HORARIOS DE MÉDICOS
-- ===============================
INSERT INTO horario (dia, horaInicio, horaFin, idMedico, usuario_creacion)
VALUES
    ('Lunes', '08:00:00', '12:00:00', 1, 'admin'),
    ('Martes', '09:00:00', '13:00:00', 2, 'admin'),
    ('Miércoles', '14:00:00', '18:00:00', 3, 'admin');

-- ===============================
-- CITAS MÉDICAS
-- ===============================
INSERT INTO citamedica (fecha, hora, id_Paciente, id_Medico, motivo, estado, usuario_creacion)
VALUES
    ('2025-08-21', '09:30:00', 1, 1, 'Dolor en el pecho', 'PENDIENTE', 'recepcion1'),
    ('2025-08-22', '10:00:00', 2, 2, 'Revisión pediátrica', 'CONFIRMADA', 'recepcion1'),
    ('2025-08-23', '15:00:00', 3, 3, 'Alergia en la piel', 'PENDIENTE', 'recepcion1'),
    ('2025-08-24', '11:00:00', 4, 4, 'Control de presión arterial', 'CONFIRMADA', 'recepcion2'),
    ('2025-08-25', '16:30:00', 5, 5, 'Dolor abdominal', 'PENDIENTE', 'recepcion2'),
    ('2025-08-26', '09:00:00', 6, 6, 'Consulta por migrañas', 'CANCELADA', 'recepcion1'),
    ('2025-08-27', '14:00:00', 7, 7, 'Control post-operatorio', 'CONFIRMADA', 'recepcion1'),
    ('2025-08-28', '08:45:00', 8, 8, 'Chequeo general', 'PENDIENTE', 'recepcion2'),
    ('2025-08-29', '10:15:00', 9, 1, 'Dolor en la rodilla', 'CONFIRMADA', 'recepcion1'),
    ('2025-08-30', '13:00:00', 10, 2, 'Problemas digestivos', 'PENDIENTE', 'recepcion2'),
    ('2025-08-31', '17:30:00', 11, 3, 'Chequeo dermatológico', 'CONFIRMADA', 'recepcion2'),
    ('2025-09-01', '11:45:00', 12, 4, 'Revisión cardiológica', 'PENDIENTE', 'recepcion1'),
    ('2025-09-02', '09:20:00', 13, 5, 'Fiebre persistente', 'CANCELADA', 'recepcion2'),
    ('2025-09-03', '15:10:00', 14, 6, 'Dolor lumbar', 'CONFIRMADA', 'recepcion1'),
    ('2025-09-04', '08:40:00', 15, 7, 'Problemas respiratorios', 'PENDIENTE', 'recepcion1'),
    ('2025-09-05', '12:30:00', 1, 8, 'Revisión de rutina', 'CONFIRMADA', 'recepcion2'),
    ('2025-09-06', '16:00:00', 2, 1, 'Consulta por mareos', 'PENDIENTE', 'recepcion2'),
    ('2025-09-07', '09:10:00', 3, 2, 'Chequeo pediátrico', 'CONFIRMADA', 'recepcion1'),
    ('2025-09-08', '14:20:00', 4, 3, 'Consulta dermatológica', 'PENDIENTE', 'recepcion1'),
    ('2025-09-09', '10:50:00', 5, 4, 'Dolor de espalda', 'CONFIRMADA', 'recepcion2'),
    ('2025-09-10', '11:15:00', 6, 5, 'Chequeo preventivo', 'PENDIENTE', 'recepcion2'),
    ('2025-09-11', '13:40:00', 7, 6, 'Control de glucosa', 'CONFIRMADA', 'recepcion1'),
    ('2025-09-12', '09:35:00', 8, 7, 'Consulta por tos crónica', 'PENDIENTE', 'recepcion1');


-- ===============================
-- HISTORIA CLÍNICA
-- ===============================
INSERT INTO historiaclinica (idPaciente, fecha, diagnostico, tratamiento, observaciones, usuario_creacion)
VALUES
    (1, '2025-08-10', 'Arritmia cardiaca', 'Medicamentos antiarrítmicos', 'Paciente con antecedentes familiares', 'drjose'),
    (2, '2025-08-12', 'Resfriado común', 'Reposo e hidratación', 'Se recomienda control en 7 días', 'drjose');

-- ===============================
-- PROVEEDORES
-- ===============================
INSERT INTO proveedor (nombre, ruc, direccion, telefono, email, usuario_creacion)
VALUES
    ('Laboratorios Perú', '20123456789', 'Av. Medicinas 111', '987111222', 'ventas@labperu.com', 'admin'),
    ('Farmacéutica Andina', '20987654321', 'Jr. Salud 222', '988222333', 'contacto@farmandina.com', 'admin');

-- ===============================
-- MEDICAMENTOS
-- ===============================
INSERT INTO medicamento (nombre, descripcion, stockActual, stockMinimo, precio, fechaVencimiento, usuario_creacion)
VALUES
    ('Paracetamol 500mg', 'Tabletas para dolor y fiebre', 100, 20, 0.50, '2026-12-31', 'admin'),
    ('Amoxicilina 500mg', 'Antibiótico de amplio espectro', 50, 10, 1.20, '2025-11-30', 'admin');

-- ===============================
-- INGRESO DE MEDICAMENTOS
-- ===============================
INSERT INTO ingreso_medicamento (idMedicamento, idProveedor, fechaIngreso, cantidad, precioCompra, usuario_creacion)
VALUES
    (1, 1, '2025-08-01', 100, 0.30, 'admin'),
    (2, 2, '2025-08-05', 50, 0.80, 'admin');

