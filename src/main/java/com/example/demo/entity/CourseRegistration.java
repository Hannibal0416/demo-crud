package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode
public class CourseRegistration {

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @Column(name = "grade")
    private int grade;

}