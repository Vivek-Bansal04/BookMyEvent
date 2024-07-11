package com.bookmyshow.api.controllers;

import com.bookmyshow.api.dtos.UserRequestDTO;
import com.bookmyshow.api.models.User;
import com.bookmyshow.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<User> createUser(@RequestBody UserRequestDTO request) {
        return ResponseEntity.ok(
                userService.createUser(request)
        );
    }
}
