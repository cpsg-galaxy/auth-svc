package com.cisco.auth.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpointAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@Order(2)
public class CustomAuthServerSecConfig extends AuthorizationServerSecurityConfiguration {

    @Qualifier("authenticationManagerBean")
    @Autowired
    private AuthenticationManager authenticationManager;

    private final OAuth2RequestFactory requestFactory;

    public CustomAuthServerSecConfig(OAuth2RequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.addFilterAfter(new TokenEndpointAuthenticationFilter(
                authenticationManager, requestFactory), BasicAuthenticationFilter.class);
    }
}
