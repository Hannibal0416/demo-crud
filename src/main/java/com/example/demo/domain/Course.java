package com.example.demo.domain;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String name;

  @ManyToMany(mappedBy = "likedCourses")
  Set<Student> likes;

  @OneToMany(mappedBy = "course")
  private Set<CourseRating> ratings = new HashSet<>();

  @OneToMany(mappedBy = "course")
  private Set<CourseRegistration> registrations = new HashSet<>();
}
