package com.cisco.auth.domains;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@Setter
public class User {

    @Id
    private String id;

    @Indexed
    private String username;

    @DBRef
    private List<Role> roles;

    private String firstName;
    private String lastName;
    private String password;
    private String email;

}
