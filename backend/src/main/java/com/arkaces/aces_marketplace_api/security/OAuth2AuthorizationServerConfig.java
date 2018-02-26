package com.arkaces.aces_marketplace_api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private UserDetailsService customUserDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private Environment environment;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer configurer) throws Exception {
        configurer.authenticationManager(authenticationManager);
        configurer.userDetailsService(customUserDetailService);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        String clientId = environment.getProperty("oauth2.clientId");
        String secret = environment.getProperty("oauth2.secret");
        Integer tokenValiditySeconds = environment.getProperty("oauth2.tokenValiditySeconds", Integer.class);
        
        clients.inMemory()
            .withClient(clientId).secret(secret)
            .accessTokenValiditySeconds(tokenValiditySeconds)
            .scopes("read", "write")
            .authorizedGrantTypes("password", "refresh_token")
            .resourceIds("account");
    }
}