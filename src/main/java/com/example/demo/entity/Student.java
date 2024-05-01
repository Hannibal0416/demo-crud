package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String name;

  @ManyToMany
  @JoinTable(name = "course_like", joinColumns = @JoinColumn(name = "student_id"),
      inverseJoinColumns = @JoinColumn(name = "course_id"),
      foreignKey = @ForeignKey(name = "course_like_student_id_key",
          value = ConstraintMode.PROVIDER_DEFAULT),
      inverseForeignKey = @ForeignKey(name = "course_like_course_id_key",
          value = ConstraintMode.PROVIDER_DEFAULT))
  Set<Course> likedCourses;


  @OneToMany(mappedBy = "student")
  private Set<CourseRating> ratings = new HashSet<>();

  @OneToMany(mappedBy = "student")
  private Set<CourseRegistration> registrations = new HashSet<>();
}
