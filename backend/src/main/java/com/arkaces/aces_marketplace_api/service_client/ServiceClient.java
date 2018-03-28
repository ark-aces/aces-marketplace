package com.arkaces.aces_marketplace_api.service_client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceClient {
    
    private final RestTemplate serviceRestTemplate;
    
    public ServiceResponse getServiceInfo(String url) {
        return serviceRestTemplate.getForObject(url, ServiceResponse.class);
    }    
    
    public RemoteContractResponse createContract(String url, String correlationId, Map<String, Object> arguments) {
        ClientCreateContractRequest clientCreateContractRequest = new ClientCreateContractRequest();
        clientCreateContractRequest.setCorrelationId(correlationId);
        clientCreateContractRequest.setArguments(arguments);
        
        return serviceRestTemplate.postForObject(url, clientCreateContractRequest, RemoteContractResponse.class);
    }

    public RemoteContractResponse getContract(String url) {
        return serviceRestTemplate.getForObject(url, RemoteContractResponse.class);
    }
}
