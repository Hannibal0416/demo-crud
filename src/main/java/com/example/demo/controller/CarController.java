package com.example.demo.controller;

import com.example.demo.controller.request.CarRequest;
import com.example.demo.controller.response.CarResponse;
import com.example.demo.controller.response.IDResponse;
import com.example.demo.service.ICarService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class CarController {

  private final ICarService carService;

  @GetMapping("cars/{id}")
  @Operation(summary = "Get a car by id", description = "Retrieve a car")
  public @ResponseStatus(HttpStatus.OK) CarResponse getCarById(@PathVariable Long id) {
    return carService.getById(id);
  }

  @GetMapping("cars")
  @Operation(summary = "Get all cars ", description = "Retrieve all cars")
  public @ResponseStatus(HttpStatus.OK) List<CarResponse> getCars(
      @RequestParam(defaultValue = "0") Integer offset,
      @RequestParam(defaultValue = "10") Integer limit) {
    log.info("offset {}, limit {}", offset, limit);
    return carService.getAll(offset, limit);
  }

  @PostMapping("cars")
  @Operation(summary = "Create a new car", description = "Create a new car")
  public @ResponseStatus(HttpStatus.CREATED) IDResponse postCar(@Valid @RequestBody CarRequest req) {
    return new IDResponse(carService.add(req));
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
