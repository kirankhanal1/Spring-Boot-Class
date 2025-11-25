package com.cosmo.training.service;

import com.cosmo.training.core.dto.ApiResponse;
import com.cosmo.training.dto.request.LoginRequest;

public interface AuthenticationService {
    ApiResponse<?> authenticate(LoginRequest loginRequest);
}
