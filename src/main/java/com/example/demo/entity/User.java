package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@Data
@Entity
@Table(name = "\"user\"")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Integer age;
  @OneToMany(mappedBy = "user")
  @Cascade(CascadeType.ALL)
  private List<Car> cars;
}
