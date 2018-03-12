package com.arkaces.aces_marketplace_api.recaptcha;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "recaptcha")
public class RecaptchaSettings {
    private String secret;
}
