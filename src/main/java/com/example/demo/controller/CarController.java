package com.example.demo.controller;

import com.example.demo.dao.Car;
import com.example.demo.request.CarRequest;
import com.example.demo.response.CarResponse;
import com.example.demo.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarController {
    @Autowired
    private CarService carService;


    @GetMapping("cars/{id}")
    @Operation(summary = "Get a car by id", description = "Retrieve a car")
    public @ResponseStatus(HttpStatus.OK) CarResponse getCarById(@PathVariable Integer id) {
        return carService.getCarById(id);
    }

    @GetMapping("cars")
    @Operation(summary = "Get all cars ", description = "Retrieve all cars")
    public @ResponseStatus(HttpStatus.OK)
        List<CarResponse> getCar(@RequestParam(defaultValue = "0") Integer offset, @RequestParam(defaultValue = "10")Integer limit) {
        return carService.getCar(offset, limit);
    }

    @PostMapping("cars")
    @Operation(summary = "Create a new car", description = "Create a new car")
    public @ResponseStatus(HttpStatus.CREATED) void postCar(@Valid @RequestBody CarRequest req) {
        carService.addCar(req);
    }

    @PostMapping("cars/{cid}/user/{uid}")
    @Operation(summary = "User buys a car", description = "User buys a car")
    public @ResponseStatus(HttpStatus.CREATED) void buyCar(@PathVariable Integer cid, @PathVariable Integer uid) {
        carService.buyCar(uid, cid);
    }

    @DeleteMapping("cars/{id}")
    @Operation(summary = "Delete a car", description = "Delete a car")
    public @ResponseStatus(HttpStatus.ACCEPTED) void deleteCarById(@PathVariable Integer id) throws DataAccessException {
        carService.deleteCar(id);
    }
}
