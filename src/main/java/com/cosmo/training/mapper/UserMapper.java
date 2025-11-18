package com.cosmo.training.mapper;

import com.cosmo.training.core.dto.SendMailRequest;
import com.cosmo.training.core.mail.service.MailService;
import com.cosmo.training.dto.request.RegisterUserRequest;
import com.cosmo.training.dto.request.UpdateUserRequest;
import com.cosmo.training.dto.response.ListUserResponse;
import com.cosmo.training.dto.response.ViewUserResponse;
import com.cosmo.training.entity.User;
import com.cosmo.training.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper {

    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    //Mapper method to create new user
    public User createUser(RegisterUserRequest registerUserRequest) {
        User user = new User();
        user.setUsername(registerUserRequest.getUsername());
        user.setPassword(registerUserRequest.getPassword());
        user.setEmail(registerUserRequest.getEmail());
        user.setFullName(registerUserRequest.getFullName());
        return user;
    }

    //Mapper method to list users
    public abstract ListUserResponse entityToResponse(User user);
    public List<ListUserResponse> listAllUsers(Page<User> users){
        return users.getContent().stream().map(this::entityToResponse).collect(Collectors.toList());
    }

    //Mapper method to view one user
    public abstract ViewUserResponse entityToViewDetails(User user);

    //Mapper method to delete user
    public User deleteUser(User user){
        user.setIsDeleted(true);
        return user;
    }

    //Mapper method to update user
    public User updateUser(User user, UpdateUserRequest updateUserRequest) {
        user.setFullName(updateUserRequest.getFullName());
        user.setEmail(updateUserRequest.getEmail());
        return user;
    }

}
