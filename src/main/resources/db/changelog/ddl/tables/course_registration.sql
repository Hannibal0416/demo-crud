--liquibase formatted sql

--changeset application:1
CREATE TABLE `course_registration` (
  `grade` int(11) DEFAULT NULL,
  `course_id` bigint(20) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `registered_at` datetime(6) DEFAULT NULL,
  `student_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `course_registration_course_id_key` (`course_id`),
  KEY `course_registration_student_id_key` (`student_id`),
  CONSTRAINT `course_registration_course_id_key` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  CONSTRAINT `course_registration_student_id_key` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
);