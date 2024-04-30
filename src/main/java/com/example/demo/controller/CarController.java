package com.example.demo.controller;

import com.example.demo.request.CarRequest;
import com.example.demo.response.CarResponse;
import com.example.demo.service.ICarService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {

  @Autowired
  private ICarService carService;

  @GetMapping("cars/{id}")
  @Operation(summary = "Get a car by id", description = "Retrieve a car")
  public @ResponseStatus(HttpStatus.OK) CarResponse getCarById(@PathVariable Long id) {
    return carService.getById(id);
  }

  @GetMapping("cars")
  @Operation(summary = "Get all cars ", description = "Retrieve all cars")
  public @ResponseStatus(HttpStatus.OK) List<CarResponse> getCar(
      @RequestParam(defaultValue = "0") Integer offset,
      @RequestParam(defaultValue = "10") Integer limit) {
    return carService.getAll(offset, limit);
  }

  @PostMapping("cars")
  @Operation(summary = "Create a new car", description = "Create a new car")
  public @ResponseStatus(HttpStatus.CREATED) void postCar(@Valid @RequestBody CarRequest req) {
    carService.add(req);
  }

  @PostMapping("cars/{cid}/user/{uid}")
  @Operation(summary = "User buys a car", description = "User buys a car")
  public @ResponseStatus(HttpStatus.CREATED) void buyCar(
      @PathVariable Long cid, @PathVariable Long uid) {
    carService.buyCar(uid, cid);
  }

  @DeleteMapping("cars/{id}")
  @Operation(summary = "Delete a car", description = "Delete a car")
  public @ResponseStatus(HttpStatus.ACCEPTED) void deleteCarById(@PathVariable Long id) {
    carService.delete(id);
  }
}
