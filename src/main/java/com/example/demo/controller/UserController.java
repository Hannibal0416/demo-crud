package com.example.demo.controller;

import com.example.demo.request.UserRequest;
import com.example.demo.response.UserResponse;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
  @Autowired
  private UserService userService;

  @GetMapping("users/{id}")
  @Operation(summary = "Get a user by id", description = "Retrieve a user")
  public UserResponse getUserById(@PathVariable Long id) {
    return userService.getById(id);
  }

  @GetMapping("users")
  @Operation(summary = "Get all users", description = "Retrieve all users")
  public List<UserResponse> getUsers(@RequestParam(defaultValue = "0") Integer offset,
      @RequestParam(defaultValue = "10") Integer limit) {
    return userService.getAll(offset, limit);
  }

  @PostMapping("users")
  @Operation(summary = "Create a user", description = "Create a user")
  public @ResponseStatus(HttpStatus.CREATED) void postUser(@Valid @RequestBody UserRequest user) {
    userService.add(user);
  }

  @PutMapping("users/{id}")
  @Operation(summary = "Update a user", description = "Update a user")
  public @ResponseStatus(HttpStatus.ACCEPTED) void putUser(@PathVariable Long id,
      @Valid @RequestBody UserRequest userVO) {
    userService.update(id, userVO);
  }

  @DeleteMapping("users/{id}")
  @Operation(summary = "Delete a user", description = "Delete a user")
  public @ResponseStatus(HttpStatus.ACCEPTED) void deleteUserById(@PathVariable Long id) {
    userService.delete(id);
  }


}
