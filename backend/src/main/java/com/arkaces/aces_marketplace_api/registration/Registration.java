package com.arkaces.aces_marketplace_api.registration;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Registration {
    private String identifier;
    private String contactEmailAddress;
    private String emailAddressVerificationCode;
    private Boolean isEmailAddressVerified;
    private LocalDateTime createdAt;
    private String createdAccountIdentifier;
}
