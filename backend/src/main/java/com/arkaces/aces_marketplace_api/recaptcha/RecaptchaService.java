package com.arkaces.aces_marketplace_api.recaptcha;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecaptchaService {
    
    private final RecaptchaSettings recaptchaSecret;
    
    private RestTemplate restTemplate = new RestTemplateBuilder().build();

    public boolean isValid(String code, String clientIp) {
        MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<>();
        parametersMap.add("secret", recaptchaSecret.getSecret());
        parametersMap.add("response", code);
        parametersMap.add("remoteip", clientIp);
        
        RecaptchaResponse recaptchaResponse = restTemplate.postForObject(
            "https://www.google.com/recaptcha/api/siteverify",
            parametersMap, 
            RecaptchaResponse.class
        );
        
        return (recaptchaResponse.getSuccess() != null && recaptchaResponse.getSuccess());
    }

}
