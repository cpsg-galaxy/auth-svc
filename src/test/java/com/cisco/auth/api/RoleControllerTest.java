package com.cisco.auth.api;

import com.cisco.auth.api.controllers.RoleController;
import com.cisco.auth.domains.Role;
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
@WebMvcTest(value = RoleController.class, secure = false)
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ModelMapper mapper;

    @Test
    public void getRolesTest() throws Exception {
        List<Role> roles = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Role role = new Role("role" + i);
            roles.add(role);
        }

        Mockito.when(userService.getRoles()).thenReturn(roles);

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/v1/roles").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "[{role: role0}, {role: role1}, {role: role2}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void createRoleTest() throws Exception {
        String samplePost = "{\"role\":\"admin\"}";
        Role mockRole = new Role();
        mockRole.setRole("admin");
        mockRole.setId("1");

        Mockito.when(userService.createRole(Mockito.any(Role.class))).thenReturn("1");
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(mockRole);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/roles")
                .accept(MediaType.APPLICATION_JSON).content(samplePost)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("http://localhost/v1/roles/1", response.getHeader(HttpHeaders.LOCATION));
    }

    @Test
    public void getRoleTest() throws Exception {
        Role mockRole = new Role();
        mockRole.setId("1");
        mockRole.setRole("admin");
        Mockito.when(userService.getRole(anyString())).thenReturn(mockRole);

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/v1/roles/1").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{role: admin}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void deleteRole() throws Exception {
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.delete("/v1/roles/1").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }
}
