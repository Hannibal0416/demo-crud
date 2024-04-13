package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
public class CourseRating {

    @EmbeddedId
    private CourseRatingKey id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "rating")
    private Integer rating;


}