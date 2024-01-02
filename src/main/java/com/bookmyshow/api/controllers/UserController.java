package com.bookmyshow.api.controllers;

import com.bookmyshow.api.dtos.CreateUserRequestDto;
import com.bookmyshow.api.dtos.CreateUserResponseDto;
import com.bookmyshow.api.models.User;
import com.bookmyshow.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
//    @Qualifier("mango")
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        System.out.printf("USER SERVICE IS BEING CALLED");
        this.userService = userService;
    }

    public CreateUserResponseDto createUser(CreateUserRequestDto request) {
        User savedUser = userService.createUser(
                request.getEmail()
        );

        CreateUserResponseDto response = new CreateUserResponseDto();
        response.setUser(savedUser);

        return response;
    }
}
