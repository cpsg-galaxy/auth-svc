package com.cisco.auth.api.models.response;

import com.cisco.auth.domains.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private List<Role> roles;
}
