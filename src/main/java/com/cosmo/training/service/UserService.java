package com.cosmo.training.service;


import com.cosmo.training.core.dto.ApiResponse;
import com.cosmo.training.dto.request.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    ResponseEntity<ApiResponse<?>> saveUser(RegisterUserRequest registerUserRequest, MultipartFile profilePicture);

    ResponseEntity<ApiResponse<?>> listUsers(PaginationRequest paginationRequest);

    ResponseEntity<ApiResponse<?>> viewUser(ViewUserRequest viewUserRequest);

    ResponseEntity<ApiResponse<?>> deleteUser(DeleteUserRequest deleteUserRequest);

    ResponseEntity<ApiResponse<?>> updateUser(UpdateUserRequest updateUserRequest);
}
