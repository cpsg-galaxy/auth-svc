package com.cisco.auth.configs;

import com.cisco.auth.repositories.UserRepository;
import com.cisco.auth.services.impl.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class CoreConfig {

    private UserRepository userRepo;

    public CoreConfig(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return new CustomUserDetailsService(userRepo);
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("C!SC0C30");

        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        DefaultUserAuthenticationConverter userTokenConverter = new DefaultUserAuthenticationConverter();
        userTokenConverter.setUserDetailsService(userDetailsService(userRepo));
        accessTokenConverter.setUserTokenConverter(userTokenConverter);

        converter.setAccessTokenConverter(accessTokenConverter);

        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
}
