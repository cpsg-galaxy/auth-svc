package com.cisco.auth.api.controllers;

import com.cisco.auth.api.models.request.RoleRequest;
import com.cisco.auth.domains.Role;
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
import java.util.Set;

@RestController
@RequestMapping("/v1/roles")
public class RoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    private UserService userService;
    private ModelMapper mapper;

    public RoleController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping
    @ApiOperation("Get All Roles")
    public ResponseEntity<Set<RoleRequest>> getRoles() {

        LOGGER.info("get all roles");
        Set<RoleRequest> roles = new HashSet<>();

        for (Role role : userService.getRoles()) {
            RoleRequest roleRequest = new RoleRequest();
            roleRequest.setRole(role.getRole());

            roles.add(roleRequest);
        }

        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Create Role")
    public ResponseEntity<Void> createRole(@RequestBody RoleRequest roleRequest, UriComponentsBuilder builder)
            throws ServiceException {

        LOGGER.info(String.format("create role: %s", roleRequest.getRole()));
        Role role = mapper.map(roleRequest, Role.class);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/v1/roles/{id}").
                buildAndExpand(userService.createRole(role)).toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiOperation("Get Role")
    public ResponseEntity<RoleRequest> getRole(@PathVariable("id") String roleId) {

        LOGGER.info(String.format("get role: %s", roleId));
        Role role = userService.getRole(roleId);

        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setRole(role.getRole());

        return new ResponseEntity<>(roleRequest, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete Role")
    public ResponseEntity<Void> deleteRole(@PathVariable("id") String roleId) {

        LOGGER.info(String.format("delete role: %s", roleId));
        userService.deleteRole(roleId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
