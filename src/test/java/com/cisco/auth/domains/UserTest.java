package com.cisco.auth.domains;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
public class UserTest {

    @Test
    public void testUserGettersAndSetters() {
        Role role = new Role("sample_role");
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        User user = new User();
        user.setId("1234567890");
        user.setUsername("jdoe");
        user.setPassword("abcdefg");
        user.setFirstName("john");
        user.setLastName("doe");
        user.setEmail("jdoe@demo.com");
        user.setRoles(roles);

        assertNotNull(user.getId());
        assertNotNull(user.getFirstName());
        assertNotNull(user.getLastName());
        assertNotNull(user.getUsername());
        assertNotNull(user.getPassword());
        assertNotNull(user.getEmail());
        assertNotNull(user.getRoles());
    }
}
