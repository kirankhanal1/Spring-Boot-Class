package com.cosmo.training.controller;

import com.cosmo.training.core.dto.ApiResponse;
import com.cosmo.training.dto.request.*;
import com.cosmo.training.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<?>> createUser(
            @RequestPart(value = "data") @Valid RegisterUserRequest registerUserRequest,
            @RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture
    ) {
        return userService.saveUser(registerUserRequest, profilePicture);
    }

    @PostMapping("/list")
    public ResponseEntity<ApiResponse<?>> listAllUsers(@RequestBody PaginationRequest paginationRequest) {
        ApiResponse<?> data = userService.listUsers(paginationRequest);
        return ResponseEntity.ok(data);
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
