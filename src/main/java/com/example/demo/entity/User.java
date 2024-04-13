package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.List;


@Data
@Entity
@Table(name = "`user`")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;
    @OneToMany(fetch = FetchType.EAGER, mappedBy="user")
    @Cascade(CascadeType.ALL)
    private List<Car> cars;
}
