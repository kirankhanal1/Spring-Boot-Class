package com.cosmo.training.service;

import com.cosmo.training.dto.RegisterUserDto;
import com.cosmo.training.entity.User;
import com.cosmo.training.exception.DuplicateEmailException;
import com.cosmo.training.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void saveUser(RegisterUserDto registerUserDto) {
        User user = new User();
        boolean existsByEmail = userRepository.existsByEmail(registerUserDto.getEmail());
        if (existsByEmail) {
            throw new DuplicateEmailException("User with email " + registerUserDto.getEmail() + " already exists");
        }
        user.setUsername(registerUserDto.getUsername());
        user.setPassword(registerUserDto.getPassword());
        user.setEmail(registerUserDto.getEmail());
        userRepository.save(user);
    }
}
