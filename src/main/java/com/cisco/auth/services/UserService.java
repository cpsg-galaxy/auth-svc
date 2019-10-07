package com.cisco.auth.services;

import com.cisco.auth.domains.Role;
import com.cisco.auth.domains.User;

import java.util.List;

public interface UserService {

    String createUser(User user);

    User getUser(String id);

    List<User> getUsers();

    User updateUser(User user);

    void deleteUser(String id);

    String createRole(Role role);

    Role getRole(String id);

    Role getRoleByRoleName(String roleName);

    List<Role> getRoles();

    void deleteRole(String id);
}
