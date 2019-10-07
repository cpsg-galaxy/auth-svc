package com.cisco.auth.api.models.request;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class UpdateRequestTest {

    @Test
    public void getterAndSetterTest() {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setPassword("test123");
        updateRequest.setUsername("jdoe");
        updateRequest.setEmail("jdoe@demo.com");

        assertEquals("jdoe", updateRequest.getUsername());
        assertEquals("test123", updateRequest.getPassword());
        assertEquals("jdoe@demo.com", updateRequest.getEmail());
    }
}
