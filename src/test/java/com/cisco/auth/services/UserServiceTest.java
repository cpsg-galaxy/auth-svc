package com.cisco.auth.services;

import com.cisco.auth.domains.Role;
import com.cisco.auth.domains.User;
import com.cisco.auth.exceptions.ServiceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.data.mongodb.host=mongo-test")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Before
    public void before() {
        List<User> users = userService.getUsers();
        for (User user : users) {
            userService.deleteUser(user.getId());
        }
        List<Role> roles = userService.getRoles();
        for (Role role : roles) {
            userService.deleteRole(role.getId());
        }
    }

    @Test
    public void testCreateRole() {
        Role role = new Role("sample_role");
        assertNotNull(userService.createRole(role));
    }

    @Test
    public void testGetRole() {
        Role role = new Role("test_role");
        String roleId = userService.createRole(role);
        assertNotNull(roleId);

        Role getRole = userService.getRole(roleId);
        assertEquals(getRole.getRole(), role.getRole());
        assertNotNull(getRole);
    }

    @Test(expected = ServiceException.class)
    public void testGetRoleException() {
        userService.getRole("abcdefg");
    }

    @Test
    public void testListRoles() {
        for (int i = 0; i < 10; i++) {
            Role role = new Role("test_role_" + i);
            userService.createRole(role);
        }

        List<Role> roles = userService.getRoles();
        assertNotNull(roles);
        assertEquals(10, roles.size());
    }

    @Test(expected = ServiceException.class)
    public void testDeleteRole() {
        Role role = new Role("delete_role");
        String roleId = userService.createRole(role);
        assertNotNull(userService.getRole(roleId));

        userService.deleteRole(roleId);
        userService.getRole(roleId);
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("password1234");
        user.setUsername("jdoe");
        user.setRoles(generateRoles());

        assertNotNull(userService.createUser(user));
    }

    @Test(expected = ServiceException.class)
    public void testCreateUserException() {
        User user = new User();
        user.setRoles(new ArrayList<>());
        userService.createUser(user);
    }

    @Test
    public void testGetUser() {
        User user = new User();
        user.setFirstName("Jane");
        user.setLastName("Jones");
        user.setPassword("password1234");
        user.setUsername("jjones");
        user.setRoles(generateRoles());
        assertNotNull(userService.createUser(user));

        User getUser = userService.getUser(user.getId());
        assertNotNull(getUser);
        assertEquals("jjones", getUser.getUsername());
    }

    @Test
    public void testGetUsers() {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUsername("demo_" + i);
            user.setPassword("pwd" + i);
            user.setRoles(generateRoles());

            userService.createUser(user);
        }

        assertNotNull(userService.getUsers());
        assertEquals(10, userService.getUsers().size());
    }

    @Test(expected = ServiceException.class)
    public void testDeleteUser() {
        User user = new User();
        user.setUsername("jdoe");
        user.setPassword("pwd123");
        user.setRoles(generateRoles());
        String userId = userService.createUser(user);
        assertNotNull(userService.getUser(userId));

        userService.deleteUser(userId);
        userService.getUser(userId);

    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setRoles(generateRoles());
        user.setFirstName("john");
        user.setLastName("doe");
        user.setUsername("jdoe");
        user.setPassword("test123");
        user.setEmail("jdoe@demo.com");

        String id = userService.createUser(user);

        User savedUser = userService.getUser(id);
        savedUser.setUsername("updateUsername");
        savedUser.setEmail("update@demo.com");

        User newUser = userService.updateUser(savedUser);

        assertEquals("updateUsername", newUser.getUsername());
        assertEquals("update@demo.com", newUser.getEmail());
    }

    @Test
    public void testGetRoleByRoleName() {
        Role role = new Role("nurse");
        userService.createRole(role);

        Role savedRole = userService.getRoleByRoleName("nurse");
        assertEquals("nurse", savedRole.getRole());
    }

    private List<Role> generateRoles() {
        Role role = new Role("sample_role");
        userService.createRole(role);
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        return roles;
    }
}
