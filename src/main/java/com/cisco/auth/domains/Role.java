package com.cisco.auth.domains;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Role {

    @Id
    private String id;

    @Indexed
    private String role;

    public Role () {}
    public Role (String role) {
        this.role = role;
    }
}
