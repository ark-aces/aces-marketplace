package com.arkaces.aces_marketplace_api.service_client;

import com.arkaces.aces_marketplace_api.services.ExchangeRate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceClient {
    
    private final RestTemplate serviceRestTemplate;
    
    public ServiceResponse getServiceInfo(String url) {
        return serviceRestTemplate.getForObject(url, ServiceResponse.class);
    }

    public ExchangeRate getServiceExchangeRate(String baseUrl, String exchangeRateHref) {
        String url = StringUtils.trimTrailingCharacter(baseUrl, '/') + exchangeRateHref;
        return serviceRestTemplate.getForObject(url, ExchangeRate.class);
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
