package com.example.demo.controller;

import com.example.demo.dao.User;
import com.example.demo.response.UserResponse;
import com.example.demo.service.UserService;
import com.example.demo.request.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("users/{id}")
    @Operation(summary = "Get a user by id", description = "Retrieve a user")
    private UserResponse getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @GetMapping("users")
    @Operation(summary = "Get all users", description = "Retrieve all users")
    public List<UserResponse> getUsers(@RequestParam(defaultValue = "0") Integer offset, @RequestParam(defaultValue = "10")Integer limit) {
        return userService.getUsers(offset, limit);
    }
    @PostMapping("users")
    @Operation(summary = "Create a user", description = "Create a user")
    public @ResponseStatus(HttpStatus.CREATED) void postUser(@Valid @RequestBody UserRequest user) {
        userService.addUser(user);
    }

    @PutMapping("users/{id}")
    @Operation(summary = "Update a user", description = "Update a user")
    public @ResponseStatus(HttpStatus.ACCEPTED) void putUser(@PathVariable Integer id,@Valid @RequestBody UserRequest userVO) throws DataAccessException {
        userService.updateUser(id, userVO);
    }

    @DeleteMapping("users/{id}")
    @Operation(summary = "Delete a user", description = "Delete a user")
    public @ResponseStatus(HttpStatus.ACCEPTED) void deleteUserById(@PathVariable Integer id) throws DataAccessException {
        userService.deleteUser(id);
    }


}
