package com.cisco.auth.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Configuration
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Qualifier("authenticationManagerBean")
    @Autowired
    private AuthenticationManager authenticationManager;

    private TokenStore tokenStore;
    private JwtAccessTokenConverter accessTokenConverter;
    private PasswordEncoder passwordEncoder;

    public AuthServerConfig(PasswordEncoder passwordEncoder, JwtAccessTokenConverter accessTokenConverter,
                        TokenStore tokenStore) {

        this.passwordEncoder = passwordEncoder;
        this.tokenStore = tokenStore;
        this.accessTokenConverter = accessTokenConverter;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .accessTokenConverter(accessTokenConverter)
                .requestFactory(requestFactory());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(customClientDetailsService());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.checkTokenAccess("isAuthenticated()");
    }

    @Bean
    public ClientDetailsService customClientDetailsService() {

        Map<String, ClientDetails> clientDetailsStore = new HashMap<>();

        // TODO: bring this out of the code
        Collection<String> scope = new HashSet<>();
        scope.add("admin");
        scope.add("customer");

        Collection<String> authorizedGrantTypes = new HashSet<>();
        authorizedGrantTypes.add("password");
        authorizedGrantTypes.add("refresh_token");
        authorizedGrantTypes.add("client_credentials");

        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId("cmo");
        clientDetails.setClientSecret(passwordEncoder.encode("secret"));
        clientDetails.setScope(scope);
        clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);

        clientDetailsStore.put("cmo", clientDetails);

        InMemoryClientDetailsService clientDetailsService = new InMemoryClientDetailsService();
        clientDetailsService.setClientDetailsStore(clientDetailsStore);

        return clientDetailsService;
    }

    @Bean
    public OAuth2RequestFactory requestFactory() {
        DefaultOAuth2RequestFactory requestFactory = new DefaultOAuth2RequestFactory(customClientDetailsService());
        requestFactory.setCheckUserScopes(true);

        return requestFactory;
    }

    @Bean
    @Primary
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore);

        return defaultTokenServices;
    }
}
