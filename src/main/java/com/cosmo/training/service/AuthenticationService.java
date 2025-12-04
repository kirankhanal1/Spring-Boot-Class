package com.cosmo.training.service;

import com.cosmo.training.core.dto.ApiResponse;
import com.cosmo.training.dto.request.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    ApiResponse<?> authenticate(LoginRequest loginRequest);
    ApiResponse<?> refreshToken(HttpServletRequest request, HttpServletResponse response);
}
