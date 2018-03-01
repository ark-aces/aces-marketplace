package com.arkaces.aces_marketplace_api.service_client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceClient {
    
    private final RestTemplate serviceRestTemplate;
    
    public ServiceResponse getServiceInfo(String url) {
        return serviceRestTemplate.getForObject(url, ServiceResponse.class);
    }    
}
