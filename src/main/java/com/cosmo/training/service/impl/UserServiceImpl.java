package com.cosmo.training.service.impl;

import com.cosmo.training.core.dto.ApiResponse;
import com.cosmo.training.core.file.service.FileService;
import com.cosmo.training.core.mail.service.EmailTemplateService;
import com.cosmo.training.core.mail.service.MailService;
import com.cosmo.training.dto.request.*;
import com.cosmo.training.dto.response.ListUserResponse;
import com.cosmo.training.dto.response.ViewUserResponse;
import com.cosmo.training.entity.User;
import com.cosmo.training.exception.DuplicateEmailException;
import com.cosmo.training.exception.FileSizeExceededException;
import com.cosmo.training.exception.NotFoundException;
import com.cosmo.training.mapper.UserMapper;
import com.cosmo.training.repository.UserRepository;
import com.cosmo.training.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MailService mailService;
    @Autowired
    private EmailTemplateService emailTemplateService;
    @Autowired
    private FileService fileService;


    @CacheEvict(value = "users", allEntries = true)
    @Override
    @Transactional
    public ApiResponse<?> saveUser(RegisterUserRequest registerUserRequest, MultipartFile profilePicture) {
        if (userRepository.existsByEmail(registerUserRequest.getEmail())) {
            log.error("Failed to save user. User with email {} already exists", registerUserRequest.getEmail());
            throw new DuplicateEmailException("User with email " + registerUserRequest.getEmail() + " already exists");
        }

        //Map and Save
        User user = userMapper.createUser(registerUserRequest);

        // upload profile picture
        if (profilePicture != null && !profilePicture.isEmpty()) {

            long maxSize = 10 * 1024 * 1024; // 10 MB in bytes

            if (profilePicture.getSize() > maxSize) {
                throw new FileSizeExceededException("File size must be less than 10MB");
            }

            String fileName = fileService.uploadFile(profilePicture);
            user.setProfilePicture(fileName);
        }


        userRepository.save(user);

        //send welcome mail to user
        emailTemplateService.sendWelcomeMail(user);

        log.info("User with email {} saved", registerUserRequest.getEmail());
        return new ApiResponse<>(true, "User saved successfully", 201);
    }

    @Cacheable(
            value = "users",
            key = "#paginationRequest.page + '-' + #paginationRequest.size + '-' + (#paginationRequest.keyword != null ? #paginationRequest.keyword : '')"
    )
    @Override
    public ApiResponse<?> listUsers(PaginationRequest paginationRequest) {
        log.info("Returning users list from database");
        Pageable pageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize(), Sort.by(Sort.Direction.DESC, "id"));
        Page<User> usersPage;
        if (paginationRequest.getKeyword()!=null  && !paginationRequest.getKeyword().trim().isEmpty()) {
            usersPage= userRepository.searchUsers(paginationRequest.getKeyword().trim(),pageable);
        }
        else {
            usersPage = userRepository.findAll(pageable);
        }

        //Map and List
        List<ListUserResponse> listUserResponse = userMapper.listAllUsers(usersPage);

        log.info("Users listed successfully");
        return new ApiResponse<>(true, "Users listed successfully", 200, listUserResponse);
    }

    @Override
    public ResponseEntity<ApiResponse<?>> viewUser(ViewUserRequest viewUserRequest) {
        Optional<User> user = userRepository.findById(viewUserRequest.getId());

        if (user.isEmpty()) {
            log.error("User with id {} not found", viewUserRequest.getId());
            throw new NotFoundException("User with id " + viewUserRequest.getId() + " not found");
        }

        //Map and view
        ViewUserResponse viewUserResponse = userMapper.entityToViewDetails(user.get());

        log.info("User with id {} viewed successfully", viewUserRequest.getId());
        ApiResponse<?> response = new ApiResponse<>(true, "User details fetched successfully", 200, viewUserResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<ApiResponse<?>> deleteUser(DeleteUserRequest deleteUserRequest) {
        Optional<User> user = userRepository.findById(deleteUserRequest.getId());

        if (user.isEmpty()) {
            log.error("User with id {} not found", deleteUserRequest.getId());
            throw new NotFoundException("User with id " + deleteUserRequest.getId() + " not found");
        }

        //Map and Save
        User userToDelete = userMapper.deleteUser(user.get());
        userRepository.save(userToDelete);
        log.info("User with id {} deleted successfully", deleteUserRequest.getId());

        ApiResponse<?> response = new ApiResponse<>(true, "User deleted successfully", 200);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<ApiResponse<?>> updateUser(UpdateUserRequest updateUserRequest) {
        Optional<User> userOptional = userRepository.findById(updateUserRequest.getId());

        if (userOptional.isEmpty()) {
            log.error("User with id {} not found", updateUserRequest.getId());
            throw new NotFoundException("User with id " + updateUserRequest.getId() + " not found");
        }

        User existingUser = userOptional.get();

        //  Email uniqueness check using Objects.equals
        if (updateUserRequest.getEmail() != null
                && !Objects.equals(updateUserRequest.getEmail(), existingUser.getEmail())) {
            Optional<User> userWithSameEmail = userRepository.findByEmail(updateUserRequest.getEmail().trim());
            if (userWithSameEmail.isPresent() && !Objects.equals(userWithSameEmail.get().getId(), existingUser.getId())) {
                log.error("Email {} already belongs to another user", updateUserRequest.getEmail());
                throw new DuplicateEmailException("Email is already taken by another user");
            }
        }

        //  Map and save
        User userToUpdate = userMapper.updateUser(existingUser, updateUserRequest);
        userRepository.save(userToUpdate);

        log.info("User with id {} updated successfully", updateUserRequest.getId());

        ApiResponse<?> response = new ApiResponse<>(true, "User updated successfully", 200);
        return ResponseEntity.ok(response);
    }

}
