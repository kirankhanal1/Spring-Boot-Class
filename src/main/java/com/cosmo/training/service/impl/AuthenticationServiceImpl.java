package com.cosmo.training.service.impl;

import com.cosmo.training.core.dto.ApiResponse;
import com.cosmo.training.dto.request.LoginRequest;
import com.cosmo.training.entity.User;
import com.cosmo.training.exception.NotFoundException;
import com.cosmo.training.repository.UserRepository;
import com.cosmo.training.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
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
            return new ApiResponse<>(true, "Logged in successfully",200);
        }
        catch (BadCredentialsException e){
            throw new BadCredentialsException("The entered password is incorrect");
        }
    }
}
