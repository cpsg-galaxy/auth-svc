package com.cisco.auth.api.models.request;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class UserRequestTest {

    @Test
    public void settersAndGettersTest() {
        List<RoleRequest> roles = new ArrayList<>();

        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("john");
        userRequest.setLastName("doe");
        userRequest.setEmail("jdoe@demo.com");
        userRequest.setPassword("test123");
        userRequest.setUsername("jdoe");
        userRequest.setRoles(roles);

        assertEquals("john", userRequest.getFirstName());
        assertEquals("doe", userRequest.getLastName());
        assertEquals("jdoe@demo.com", userRequest.getEmail());
        assertEquals("test123", userRequest.getPassword());
        assertEquals(0, userRequest.getRoles().size());
        assertEquals("jdoe", userRequest.getUsername());
    }
}
