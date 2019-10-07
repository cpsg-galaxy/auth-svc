package com.cisco.auth.services.impl;

import com.cisco.auth.domains.Role;
import com.cisco.auth.domains.User;
import com.cisco.auth.repositories.RoleRepository;
import com.cisco.auth.repositories.UserRepository;
import com.cisco.auth.services.UserService;
import com.cisco.auth.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepo;
    private RoleRepository roleRepo;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String createUser(User user) throws ServiceException {
        if (user.getRoles().size() == 0) {
            LOGGER.error(String.format("user has no role: %s", user.getUsername()));
            throw new ServiceException(User.class, "user has no role");
        }

        for (Role role : user.getRoles()) {
            if (StringUtils.isEmpty(role.getId())) {
                LOGGER.error(String.format("role does not exist: %s", role.getRole()));
                throw new ServiceException(User.class, "role does not exist");
            }
        }

        // Encode the Password then save the User
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepo.save(user);

        LOGGER.info(String.format("user saved: %s, %s", user.getId(), user.getUsername()));
        return savedUser.getId();
    }

    @Override
    public User getUser(String id) throws ServiceException {
        Optional<User> user = userRepo.findById(id);
        if (!user.isPresent()) {
            LOGGER.error(String.format("not able to get user based on ID: %s", id));
            throw new ServiceException(User.class, "not able to get user");
        }

        return user.get();
    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public User updateUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public void deleteUser(String id) {
        userRepo.deleteById(id);
    }

    @Override
    public String createRole(Role role) {
        Role savedRole = roleRepo.save(role);
        return savedRole.getId();
    }

    @Override
    public Role getRole(String id) {
        Optional<Role> role = roleRepo.findById(id);
        if (!role.isPresent()) {
            LOGGER.error(String.format("not able to get role based of ID: %s", id));
            throw new ServiceException(Role.class, "not able to get role");
        }

        return role.get();
    }

    @Override
    public Role getRoleByRoleName(String roleName) {
        return roleRepo.findRoleByRole(roleName);
    }

    @Override
    public List<Role> getRoles() {
        return roleRepo.findAll();
    }

    @Override
    public void deleteRole(String id) throws ServiceException {

        if (userRepo.findUserByRoleId(id) != null) {
            throw new ServiceException(Role.class, "role is being used");
        }

        roleRepo.deleteById(id);
    }
}