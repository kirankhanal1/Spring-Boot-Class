package com.cosmo.training.service.impl;

import com.cosmo.training.core.dto.ApiResponse;
import com.cosmo.training.core.security.JwtService;
import com.cosmo.training.dto.request.LoginRequest;
import com.cosmo.training.dto.response.AuthenticationResponse;
import com.cosmo.training.entity.User;
import com.cosmo.training.exception.InvalidTokenException;
import com.cosmo.training.exception.NotFoundException;
import com.cosmo.training.repository.UserRepository;
import com.cosmo.training.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    @Override
    public ApiResponse<?> authenticate(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());

        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            String accessToken = jwtService.generateAccessToken(user.get());
            String refreshToken = jwtService.generateRefreshToken(user.get());

            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setAccessToken(accessToken);
            authenticationResponse.setRefreshToken(refreshToken);

            return new ApiResponse<>(true, "Logged in successfully",200,authenticationResponse);
        }
        catch (BadCredentialsException e){
            throw new BadCredentialsException("The entered password is incorrect");
        }
    }

    @Override
    public ApiResponse<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("Failed to find refresh token. Please log in again to continue.");
        }
        String refreshTokenInHeader = authHeader.substring(7);
        Optional<User> user = userRepository.findByEmail(jwtService.extractEmail(refreshTokenInHeader));
        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        boolean isRefreshTokenValid = jwtService.validateRefreshToken(refreshTokenInHeader, user.get().getEmail());
        if (isRefreshTokenValid){
            String accessToken = jwtService.generateAccessToken(user.get());
            String refreshToken = jwtService.generateRefreshToken(user.get());

            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setAccessToken(accessToken);
            authenticationResponse.setRefreshToken(refreshToken);

            return new ApiResponse<>(true, "Token refreshed successfully",200,authenticationResponse);
        }
        throw new InvalidTokenException("The refresh token is invalid. Please log in again to continue.");
    }
}
