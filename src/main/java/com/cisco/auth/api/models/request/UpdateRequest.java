package com.cisco.auth.api.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequest {

    private String username;
    private String password;
    private String email;
}
