package com.cisco.auth.services;

import com.cisco.auth.domains.Role;
import com.cisco.auth.domains.User;
import com.cisco.auth.repositories.UserRepository;
import com.cisco.auth.services.impl.CustomUserDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
public class UserDetailsServiceTest {

    private CustomUserDetailsService userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setup() {
        userDetailsService = new CustomUserDetailsService(userRepository);
    }

    @Test
    public void loadUserByUsernameTest() {
        Role mockRole = new Role("nurse");
        List<Role> mockRoles = new ArrayList<>();
        mockRoles.add(mockRole);

        User mockUser = new User();
        mockUser.setUsername("jdoe");
        mockUser.setPassword("test123");
        mockUser.setRoles(mockRoles);

        Mockito.when(userRepository.findByUserNameWithRole(anyString())).thenReturn(mockUser);
        UserDetails userDetails = userDetailsService.loadUserByUsername("jdoe");

        assertEquals("jdoe", userDetails.getUsername());
        assertEquals("test123", userDetails.getPassword());
    }
}