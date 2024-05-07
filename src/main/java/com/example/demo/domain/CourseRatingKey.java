package com.example.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Embeddable
@Data
@EqualsAndHashCode
public class CourseRatingKey implements Serializable {

  @Column(name = "student_id")
  private Long studentId;

  @Column(name = "course_id")
  private Long courseId;
}
