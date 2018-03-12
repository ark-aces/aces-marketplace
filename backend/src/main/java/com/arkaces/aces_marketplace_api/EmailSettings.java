package com.arkaces.aces_marketplace_api;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "email")
public class EmailSettings {
    private String fromName;
    private String fromEmailAddress;
}
