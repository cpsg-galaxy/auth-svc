package com.cisco.auth.api.models.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRequest {

    private String username;
    private List<RoleRequest> roles;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
}
