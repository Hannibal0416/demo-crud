package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@Data
@EqualsAndHashCode
public class CourseRatingKey implements Serializable {

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "course_id")
    private Long courseId;

}