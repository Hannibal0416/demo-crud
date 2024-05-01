package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
public class CourseRegistration {

  @Id
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "student_id",
      foreignKey = @ForeignKey(name = "course_registration_student_id_key",
          value = ConstraintMode.PROVIDER_DEFAULT))
  private Student student;

  @ManyToOne
  @JoinColumn(name = "course_id",
      foreignKey = @ForeignKey(name = "course_registration_course_id_key",
          value = ConstraintMode.PROVIDER_DEFAULT))
  private Course course;

  @Column(name = "registered_at")
  private LocalDateTime registeredAt;

  @Column(name = "grade")
  private int grade;

}
