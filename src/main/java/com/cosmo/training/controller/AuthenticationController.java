package com.cosmo.training.controller;

import com.cosmo.training.core.dto.ApiResponse;
import com.cosmo.training.dto.request.LoginRequest;
import com.cosmo.training.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> authenticate(@Valid @RequestBody LoginRequest loginRequest)  {
        return ResponseEntity.ok(authenticationService.authenticate(loginRequest));
    }
}
