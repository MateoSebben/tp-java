CREATE DATABASE IF NOT EXISTS `tpjava` CHARACTER SET utf8mb4;

USE `tpjava`;

-- Tabla Facultad
DROP TABLE IF EXISTS `facultad`;

CREATE TABLE `facultad` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  `direccion` VARCHAR(255),
  `emailContacto` VARCHAR(255),
  `telefono` VARCHAR(255) DEFAULT NULL,
  `fechaAlta` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla Usuario
DROP TABLE IF EXISTS `usuario`;

CREATE TABLE `usuario` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  `apellido` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255),
  `password` VARCHAR(255) DEFAULT NULL,
  `salt` VARCHAR(255) NOT NULL,
  `rol` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla Carrera
DROP TABLE IF EXISTS `carrera`;

CREATE TABLE `carrera` (
  `idCarrera` INT(11) NOT NULL AUTO_INCREMENT,
  `nombreCarrera` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`idCarrera`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla Materia
DROP TABLE IF EXISTS `materia`;

CREATE TABLE `materia` (
  `idMateria` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`idMateria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla Archivo
DROP TABLE IF EXISTS `archivo`;

CREATE TABLE `archivo` (
  `idArchivo` INT(11) NOT NULL AUTO_INCREMENT,
  `idUsuario` INT(11) NOT NULL,
  `idMateria` INT(11) NOT NULL,
  `idCarrera` INT(11) NOT NULL,
  `nombre` VARCHAR(255) NOT NULL,
  `descripcion` TEXT,
  `peso` FLOAT DEFAULT NULL,
  `anioCursada` INT(11) NOT NULL,
  `tipoArchivo` VARCHAR(50) DEFAULT NULL,
  `esFisico` BOOLEAN DEFAULT FALSE,
  `fechaSubida` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `extension` VARCHAR(10) DEFAULT NULL,
  `nombreFisico` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`idArchivo`),
  CONSTRAINT `fk_archivo_usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuario`(`id`) 
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_archivo_materia` FOREIGN KEY (`idMateria`) REFERENCES `materia`(`idMateria`)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_archivo_carrera` FOREIGN KEY (`idCarrera`) REFERENCES `carrera`(`idCarrera`)
	ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla Solicitud_Materia
DROP TABLE IF EXISTS `solicitud_materia`;

CREATE TABLE `solicitud_materia` (
  `idSolicitud` INT(11) NOT NULL AUTO_INCREMENT,
  `nombreMateria` VARCHAR(255) NOT NULL,
  `descripcion` TEXT DEFAULT NULL,
  `idCarrera` INT(11) NOT NULL,
  `idUsuarioSolicitante` INT(11) NOT NULL,
  `fechaSolicitud` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `estado` VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
  `motivoRechazo` TEXT DEFAULT NULL,
  `idAdministrador` INT(11) DEFAULT NULL,
  `fechaResolucion` DATETIME DEFAULT NULL,
  PRIMARY KEY (`idSolicitud`),
  FOREIGN KEY (`idCarrera`) REFERENCES `carrera`(`idCarrera`),
  FOREIGN KEY (`idUsuarioSolicitante`) REFERENCES `usuario`(`id`),
  FOREIGN KEY (`idAdministrador`) REFERENCES `usuario`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla Carrera-Materia 
DROP TABLE IF EXISTS `carrera_materia`;

CREATE TABLE `carrera_materia` (
  `idCarrera` INT(11) NOT NULL,
  `idMateria` INT(11) NOT NULL,
  `fecha` DATE DEFAULT NULL,
  PRIMARY KEY (`idCarrera`, `idMateria`),
  CONSTRAINT `fk_carrera_materia_carrera` FOREIGN KEY (`idCarrera`) REFERENCES `carrera`(`idCarrera`) 
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_carrera_materia_materia` FOREIGN KEY (`idMateria`) REFERENCES `materia`(`idMateria`) 
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla Carrera-Facultad 
DROP TABLE IF EXISTS `carrera_facultad`;

CREATE TABLE `carrera_facultad` (
  `idCarrera` INT(11) NOT NULL,
  `idFacultad` INT(11) NOT NULL,
  `fecha` DATE DEFAULT NULL,
  PRIMARY KEY (`idCarrera`, `idFacultad`),
  CONSTRAINT `fk_carrera_facultad_carrera` FOREIGN KEY (`idCarrera`) REFERENCES `carrera`(`idCarrera`) 
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_carrera_facultad_facultad` FOREIGN KEY (`idFacultad`) REFERENCES `facultad`(`id`) 
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;




