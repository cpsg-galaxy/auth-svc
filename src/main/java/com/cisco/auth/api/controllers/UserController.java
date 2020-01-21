package com.cisco.auth.api.controllers;

import com.cisco.auth.api.models.request.UpdateRequest;
import com.cisco.auth.api.models.request.UserRequest;
import com.cisco.auth.api.models.response.UserResponse;
import com.cisco.auth.domains.User;
import com.cisco.auth.exceptions.ServiceException;
import com.cisco.auth.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private UserService userService;
    private ModelMapper mapper;

    public UserController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping
    @ApiOperation("Get All Users")
    public ResponseEntity<Set<UserResponse>> getUsers() {

        LOGGER.info("get all users");
        List<User> users = userService.getUsers();
        Set<UserResponse> result = new HashSet<>();

        for (User user : users) {
            result.add(mapper.map(user, UserResponse.class));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Create User")
    public ResponseEntity<Void> createUser(@RequestBody UserRequest userRequest, UriComponentsBuilder builder)
            throws ServiceException {

        LOGGER.info(String.format("create user: %s", userRequest.getUsername()));
        User user = mapper.map(userRequest, User.class);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/v1/users/{id}").
                buildAndExpand(userService.createUser(user)).toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") String userId) {

        LOGGER.info(String.format("get user: %s", userId));

        User user = userService.getUser(userId);
        UserResponse userResponse = mapper.map(user, UserResponse.class);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String userId) {

        LOGGER.info(String.format("delete user: %s", userId));
        userService.deleteUser(userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") String userId, @RequestBody UpdateRequest updateRequest) {

        LOGGER.info(String.format("update user: %s", userId));
        User user = userService.getUser(userId);

        user.setPassword(updateRequest.getPassword());
        user.setEmail(updateRequest.getEmail());
        user.setUsername(updateRequest.getUsername());

        userService.updateUser(user);
        UserResponse userResponse = mapper.map(user, UserResponse.class);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
