package com.cosmo.training.service;


import com.cosmo.training.core.dto.ApiResponse;
import com.cosmo.training.dto.RegisterUserDto;
import com.cosmo.training.dto.UpdateUserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ApiResponse<?>> saveUser(RegisterUserDto registerUserDto);

    ResponseEntity<ApiResponse<?>> listUsers();

    ResponseEntity<ApiResponse<?>> getUserById(Integer id);

    ResponseEntity<ApiResponse<?>> deleteUserById(Integer id);

    ResponseEntity<ApiResponse<?>> updateUser(UpdateUserDto updateUserDto);
}
