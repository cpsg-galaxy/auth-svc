package com.cisco.auth.configs;

import com.cisco.auth.web.CORSFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@Configuration
@EnableWebSecurity
@EnableResourceServer
public class SecurityConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class).
                authorizeRequests().
                antMatchers(HttpMethod.OPTIONS, "/**").permitAll().
                antMatchers("/user").authenticated().
                antMatchers("/**").permitAll();
    }
}
