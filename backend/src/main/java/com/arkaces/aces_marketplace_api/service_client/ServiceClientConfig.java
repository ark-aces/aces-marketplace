package com.arkaces.aces_marketplace_api.service_client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ServiceClientConfig {
    
    @Bean
    public RestTemplate serviceRestTemplate() {
        return new RestTemplateBuilder()
            .build();
    }
}
