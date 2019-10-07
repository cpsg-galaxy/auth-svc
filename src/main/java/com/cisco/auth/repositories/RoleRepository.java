package com.cisco.auth.repositories;

import com.cisco.auth.domains.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {

    Role findRoleByRole(String role);
}
