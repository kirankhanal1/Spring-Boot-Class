package com.cosmo.training.service.impl;

import com.cosmo.training.core.dto.ApiResponse;
import com.cosmo.training.dto.ListUserDto;
import com.cosmo.training.dto.RegisterUserDto;
import com.cosmo.training.dto.UpdateUserDto;
import com.cosmo.training.entity.User;
import com.cosmo.training.exception.DuplicateEmailException;
import com.cosmo.training.exception.NotFoundException;
import com.cosmo.training.repository.UserRepository;
import com.cosmo.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<ApiResponse<?>> saveUser(RegisterUserDto registerUserDto) {
        if (userRepository.existsByEmail(registerUserDto.getEmail())) {
            throw new DuplicateEmailException("User with email " + registerUserDto.getEmail() + " already exists");
        }

        User user = new User();
        user.setUsername(registerUserDto.getUsername());
        user.setPassword(registerUserDto.getPassword());
        user.setEmail(registerUserDto.getEmail());
        user.setFullName(registerUserDto.getFullName());

        userRepository.save(user);

        ApiResponse<?> response = new ApiResponse<>(true, "User saved successfully", 201);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @Override
    public ResponseEntity<ApiResponse<?>> listUsers() {
        List<User> users = userRepository.findAll();

        List<ListUserDto> listUserDtos = new ArrayList<>();

        for (User user : users) {
            ListUserDto listUserDto = new ListUserDto();
            listUserDto.setUsername(user.getUsername());
            listUserDto.setEmail(user.getEmail());
            listUserDto.setFullName(user.getFullName());

            listUserDtos.add(listUserDto);
        }
        ApiResponse<?> response = new ApiResponse<>(true, "Users listed successfully", 200, listUserDtos);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<ApiResponse<?>> getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new NotFoundException("User with id " + id + " not found");
        }

        ApiResponse<?> response = new ApiResponse<>(true, "User details fetched successfully", 200, user.get());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<ApiResponse<?>> deleteUserById(Integer id) {
        userRepository.deleteById(id);

        ApiResponse<?> response = new ApiResponse<>(true, "User deleted successfully", 200);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<ApiResponse<?>> updateUser(UpdateUserDto updateUserDto) {
        Optional<User> user = userRepository.findById(updateUserDto.getId());

        User userToUpdate = user.get();

        userToUpdate.setEmail(updateUserDto.getEmail());
        userToUpdate.setFullName(updateUserDto.getFullName());
        userRepository.save(userToUpdate);

        ApiResponse<?> response = new ApiResponse<>(true, "User updated successfully", 200);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
