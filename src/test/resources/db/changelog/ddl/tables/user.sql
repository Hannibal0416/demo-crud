--liquibase formatted sql

--changeset application:1
CREATE TABLE `user` (
  `age` int(11) DEFAULT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);