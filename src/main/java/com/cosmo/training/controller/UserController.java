package com.cosmo.training.controller;

import com.cosmo.training.core.dto.ApiResponse;
import com.cosmo.training.dto.request.*;
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
    public ResponseEntity<ApiResponse<?>> createUser(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        return userService.saveUser(registerUserRequest);
    }

    @PostMapping("/list")
    public ResponseEntity<ApiResponse<?>> listAllUsers(@RequestBody PaginationRequest paginationRequest) {
        return userService.listUsers(paginationRequest);
    }

    @PostMapping("/view")
    public ResponseEntity<ApiResponse<?>> getUserById(@RequestBody @Valid ViewUserRequest viewUserRequest) {
        return userService.viewUser(viewUserRequest);
    }

    @PostMapping("/delete")
    public ResponseEntity<ApiResponse<?>> deleteById(@RequestBody @Valid DeleteUserRequest deleteUserRequest) {
        return userService.deleteUser(deleteUserRequest);
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<?>> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
        return userService.updateUser(updateUserRequest);
    }
}
