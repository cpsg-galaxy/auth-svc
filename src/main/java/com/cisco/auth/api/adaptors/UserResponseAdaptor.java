package com.cisco.auth.api.adaptors;

import com.cisco.auth.api.models.response.UserResponse;
import com.cisco.auth.domains.User;
import com.github.jmnarloch.spring.boot.modelmapper.ConverterConfigurerSupport;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserResponseAdaptor extends ConverterConfigurerSupport<User, UserResponse> {
    @Override
    protected Converter<User, UserResponse> converter() {
        return new AbstractConverter<User, UserResponse>() {

            @Override
            protected UserResponse convert(User source) {
                UserResponse userResponse = new UserResponse();
                userResponse.setId(source.getId());
                userResponse.setEmail(source.getEmail());
                userResponse.setFirstName(source.getFirstName());
                userResponse.setLastName(source.getLastName());
                userResponse.setUsername(source.getUsername());
                userResponse.setRoles(source.getRoles());

                return userResponse;
            }
        };
    }
}
