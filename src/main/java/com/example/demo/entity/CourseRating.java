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
    @JoinColumn(name = "student_id", foreignKey= @ForeignKey(name="course_rating_student_id_key", value = ConstraintMode.PROVIDER_DEFAULT))
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id", foreignKey= @ForeignKey(name="course_rating_course_id_key", value = ConstraintMode.PROVIDER_DEFAULT))
    private Course course;

    @Column(name = "rating")
    private Integer rating;


}