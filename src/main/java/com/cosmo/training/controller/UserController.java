package com.cosmo.training.controller;

import com.cosmo.training.dto.RegisterUserDto;
import com.cosmo.training.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public ResponseEntity<String> saveUser(@RequestBody @Valid RegisterUserDto registerUserDto) {
        userService.saveUser(registerUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
}
