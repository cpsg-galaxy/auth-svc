package com.cisco.auth.domains;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
public class RoleTest {

    @Test
    public void testRoleGettersAndSetters() {
        Role role = new Role();
        role.setId("1234567890");
        role.setRole("sample_role");
        assertNotNull(role.getId());
        assertNotNull(role.getRole());

        Role role2 = new Role("sample_role2");
        role2.setId("0123456789");
        assertNotNull(role2.getId());
        assertNotNull(role2.getRole());
    }
}
