package com.arkaces.aces_marketplace_api.services;

import com.arkaces.aces_marketplace_api.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceController {
    
    private final RestTemplate serviceRestTemplate;
    
    @PostMapping
    public Service createService(
        @AuthenticationPrincipal UserEntity userEntity, 
        @RequestBody CreateServiceRequest createServiceRequest
    ) {
        throw new RuntimeException("not implemented!");
    }

}
