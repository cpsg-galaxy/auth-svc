package com.cisco.auth.repositories;

import com.cisco.auth.domains.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{ 'username' : ?0 }")
    User findByUserNameWithRole(String username);

    @Query("{ 'roles.id' : ?0 }")
    User findUserByRoleId(String roleId);

}
