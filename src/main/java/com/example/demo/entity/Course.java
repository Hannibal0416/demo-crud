package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode
class Course {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    String name;

    @ManyToMany(mappedBy = "likedCourses")
    Set<Student> likes;

    @OneToMany(mappedBy = "course")
    private Set<CourseRating> ratings = new HashSet<>();

    @OneToMany(mappedBy = "course")
    private Set<CourseRegistration> registrations = new HashSet<>();

}