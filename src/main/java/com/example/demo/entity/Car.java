package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
