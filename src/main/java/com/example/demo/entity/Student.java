package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode
public class Student {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    String name;

    @ManyToMany
    @JoinTable( name = "course_like",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    Set<Course> likedCourses;


    @OneToMany(mappedBy = "student")
    private Set<CourseRating> ratings = new HashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<CourseRegistration> registrations = new HashSet<>();
}
