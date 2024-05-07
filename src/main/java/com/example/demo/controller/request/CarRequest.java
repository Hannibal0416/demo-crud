package com.example.demo.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CarRequest {
  @NotNull private String model;
}
