package com.cisco.auth.api.models.request;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class RoleRequestTest {

    @Test
    public void getterAndSetterTest() {
        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setRole("nurse");

        assertEquals("nurse", roleRequest.getRole());
    }
}
