package com.cisco.auth.api.models.response;

import com.cisco.auth.domains.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class UserResponseTest {

    @Test
    public void getterAndSetterTest() {
        UserResponse userResponse = createUserResponse();

        assertEquals("ID0", userResponse.getId());
        assertEquals("john", userResponse.getFirstName());
        assertEquals("doe", userResponse.getLastName());
        assertEquals("jdoe@demo.com", userResponse.getEmail());
        assertEquals("jdoe", userResponse.getUsername());
        assertEquals(0, userResponse.getRoles().size());
    }

    @Test
    public void toStringTest() {
        UserResponse userResponse = createUserResponse();

        assertNotNull(userResponse.toString());
    }

    @Test
    public void equalsTest() {
        UserResponse firstUserResponse = createUserResponse();
        UserResponse secondUserResponse = createUserResponse();

        assertTrue(firstUserResponse.equals(secondUserResponse));

    }

    private UserResponse createUserResponse() {
        List<Role> roles = new ArrayList<>();

        UserResponse userResponse = new UserResponse();
        userResponse.setId("ID0");
        userResponse.setFirstName("john");
        userResponse.setLastName("doe");
        userResponse.setEmail("jdoe@demo.com");
        userResponse.setUsername("jdoe");
        userResponse.setRoles(roles);

        return userResponse;
    }
}
