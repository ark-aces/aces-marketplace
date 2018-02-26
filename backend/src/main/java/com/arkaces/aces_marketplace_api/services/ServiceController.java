package com.arkaces.aces_marketplace_api.services;

import com.arkaces.aces_marketplace_api.common.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceController {
    
    @GetMapping("/services")
    public Page<Service> createService() {
        throw new RuntimeException("not implemented!");
    }

}
