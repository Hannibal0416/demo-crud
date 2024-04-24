--liquibase formatted sql

--changeset application:1
CREATE TABLE `course_like` (
  `course_id` bigint(20) NOT NULL,
  `student_id` bigint(20) NOT NULL,
  PRIMARY KEY (`course_id`,`student_id`),
  KEY `course_like_student_id_key` (`student_id`),
  CONSTRAINT `course_like_course_id_key` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  CONSTRAINT `course_like_student_id_key` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
);