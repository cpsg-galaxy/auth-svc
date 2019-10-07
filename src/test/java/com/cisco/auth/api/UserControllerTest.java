package com.cisco.auth.api;

import com.cisco.auth.api.controllers.UserController;
import com.cisco.auth.api.models.response.UserResponse;
import com.cisco.auth.domains.Role;
import com.cisco.auth.domains.User;
import com.cisco.auth.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ModelMapper mapper;

    @Test
    public void createUserTest() throws Exception {
        String samplePost = "{\"username\":\"jdoe\", \"firstName\":\"john\"," +
                "\"lastName\": \"doe\", \"password\":\"test1234\", " +
                "\"email\": \"jdoe@demo.com\", \"roles\": [{\"role\": \"admin\"}]}";

        List<Role> roles = new ArrayList<>();
        Role mockRole = new Role();
        mockRole.setRole("admin");
        mockRole.setId("1");
        roles.add(mockRole);

        User mockUser = new User();
        mockUser.setId("1");
        mockUser.setEmail("jdoe@demo.com");
        mockUser.setUsername("jdoe");
        mockUser.setFirstName("john");
        mockUser.setLastName("doe");
        mockUser.setPassword("password");
        mockUser.setRoles(roles);

        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn("1");
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(mockUser);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/users")
                .accept(MediaType.APPLICATION_JSON).content(samplePost)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("http://localhost/v1/users/1", response.getHeader(HttpHeaders.LOCATION));
    }

    @Test
    public void getUsersTest() throws Exception {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            Role role = new Role();
            role.setId("ID" + i);
            role.setRole("role"+i);

            List<Role> roles = new ArrayList<>();
            roles.add(role);

            User user = new User();
            user.setFirstName("firstName" + i);
            user.setLastName("lastName" + i);
            user.setUsername("username" + i);
            user.setEmail("email" + i);
            user.setId("ID" + i);
            user.setRoles(roles);

            users.add(user);
        }

        Mockito.when(userService.getUsers()).thenReturn(users);
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(createMockUserResponse());

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/v1/users").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "[{firstName: firstName0, lastName: lastName0, username: username0, id: ID0, email: email0, " +
                "roles:[{id: ID0, role: role0}]}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getUserTest() throws Exception {
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(createMockUserResponse());
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/v1/users/ID0").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{firstName: firstName0, lastName: lastName0, username: username0, id: ID0, email: email0, " +
                "roles:[{id: ID0, role: role0}]}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void deleteUserTest() throws Exception {
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.delete("/v1/users/ID0").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    public void updateUserTest() throws Exception {
        Mockito.when(userService.getUser(anyString())).thenReturn(new User());

        String samplePost = "{\"username\":\"jdoe\", \"password\":\"test1234\", \"email\": \"jdoe@demo.com\"}";
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.patch("/v1/users/ID0")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(samplePost)
                        .contentType(MediaType.APPLICATION_JSON);
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(createMockUserResponse());

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{firstName: firstName0, lastName: lastName0, username: username0, id: ID0, email: email0, " +
                "roles:[{id: ID0, role: role0}]}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    private UserResponse createMockUserResponse() {
        Role role = new Role();
        role.setId("ID0");
        role.setRole("role0");

        List<Role> roles = new ArrayList<>();
        roles.add(role);

        UserResponse mockUserResponse = new UserResponse();
        mockUserResponse.setId("ID0");
        mockUserResponse.setUsername("username0");
        mockUserResponse.setFirstName("firstName0");
        mockUserResponse.setLastName("lastName0");
        mockUserResponse.setEmail("email0");
        mockUserResponse.setRoles(roles);

        return mockUserResponse;
    }
}