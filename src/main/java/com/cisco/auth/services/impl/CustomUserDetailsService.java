package com.cisco.auth.services.impl;

import com.cisco.auth.models.CustomUserDetails;
import com.cisco.auth.domains.Role;
import com.cisco.auth.domains.User;
import com.cisco.auth.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final User user = userRepo.findByUserNameWithRole(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username: '%s'", username));
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }

        CustomUserDetails userDetails = new CustomUserDetails(user.getUsername(), user.getPassword(), authorities);
        userDetails.setId(user.getId());
        userDetails.setUsername(user.getUsername());

        return userDetails;
    }
}
