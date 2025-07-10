CREATE DATABASE IF NOT EXISTS `tpjava` CHARACTER SET utf8mb4;

USE `tpjava`;

DROP TABLE IF EXISTS `facultad`;

CREATE TABLE `facultad` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `direccion` varchar(255),
  `emailContacto` varchar(255),
  `telefono` varchar(255) DEFAULT NULL,
  `fechaAlta` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `facultad` (nombre, direccion, emailContacto, telefono)
VALUES 
('Universidad Tecnologica Nacional', 'Zeballos 1340', 'contacto@fi.edu.ar', '03464-123456'),
('Universidad Nacional de Rosario', 'Calle Principal 456', 'info@fcs.edu.ar', '03464-654321'),
('Universidad Abierta Interamericana', 'Av. Cultura 789', 'humanidades@uni.edu', '03464-789123'),
('Universidad Nacional de La Plata', 'Diagonal Norte 1010', 'economicas@uni.edu', '03464-112233'),
('Universidad de Buenos Aires', 'Av. Diseño 202', 'arquitectura@uni.edu', '03464-556677'),
('Universidad del Gran Rosario', 'Pasaje Legal 12', 'derecho@uni.edu', '03464-998877');