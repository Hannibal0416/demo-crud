--liquibase formatted sql

--changeset application:1
CREATE TABLE `car` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `car_user_id_key` (`user_id`),
  CONSTRAINT `car_user_id_key` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);