package com.cisco.auth.api.adaptors;

import com.cisco.auth.api.models.request.RoleRequest;
import com.cisco.auth.domains.Role;
import com.github.jmnarloch.spring.boot.modelmapper.ConverterConfigurerSupport;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleRequestAdaptor extends ConverterConfigurerSupport<RoleRequest, Role> {

    @Override
    protected Converter<RoleRequest, Role> converter() {
        return new AbstractConverter<RoleRequest, Role>() {

            @Override
            protected Role convert(RoleRequest source) {
                return new Role(source.getRole());
            }
        };
    }
}
