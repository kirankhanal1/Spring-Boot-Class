package com.cosmo.training.controller;

import com.cosmo.training.core.dto.ApiResponse;
import com.cosmo.training.dto.RegisterUserDto;
import com.cosmo.training.dto.UpdateUserDto;
import com.cosmo.training.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> saveUser(@RequestBody @Valid RegisterUserDto registerUserDto) {
        return userService.saveUser(registerUserDto);
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<?>> getAllUser() {
        return userService.listUsers();
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResponse<?>> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteById(@PathVariable Integer id) {
        return userService.deleteUserById(id);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<?>> updateUser(@RequestBody @Valid UpdateUserDto updateUserDto) {
        return userService.updateUser(updateUserDto);
    }
}
