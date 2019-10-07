package com.cisco.auth.api.adaptors;

import com.cisco.auth.api.models.request.RoleRequest;
import com.cisco.auth.api.models.request.UserRequest;
import com.cisco.auth.domains.Role;
import com.cisco.auth.domains.User;
import com.cisco.auth.services.UserService;
import com.github.jmnarloch.spring.boot.modelmapper.ConverterConfigurerSupport;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRequestAdaptor extends ConverterConfigurerSupport<UserRequest, User> {

    private UserService userService;

    public UserRequestAdaptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected Converter<UserRequest, User> converter() {
        return new AbstractConverter<UserRequest, User>() {

            @Override
            protected User convert(UserRequest source) {
                List<Role> roles = new ArrayList<>();
                for (RoleRequest roleRequest : source.getRoles()) {
                    Role role = userService.getRoleByRoleName(roleRequest.getRole());
                    if (role != null) {
                        roles.add(role);
                    }
                }

                User user = new User();
                user.setFirstName(source.getFirstName());
                user.setLastName(source.getLastName());
                user.setUsername(source.getUsername());
                user.setEmail(source.getEmail());
                user.setPassword(source.getPassword());
                user.setRoles(roles);

                return user;
            }
        };
    }
}
