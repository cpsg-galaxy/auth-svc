package com.cisco.auth.modeles;

import com.cisco.auth.models.CustomUserDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.data.mongodb.host=mongo-test")
public class CustomUserDetailsTest {

    private static final String USERNAME = "jdoe";

    @Test
    public void testGettersAndSetters() {
        List<GrantedAuthority> roles = new ArrayList<>();
        CustomUserDetails userDetails = new CustomUserDetails(USERNAME, "test123", roles);
        userDetails.setId("ID0");
        userDetails.setUsername(USERNAME);

        assertEquals(USERNAME, userDetails.getUsername());
        assertEquals("ID0", userDetails.getId());
    }
}
