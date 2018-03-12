package com.arkaces.aces_marketplace_api.registration;

import lombok.Data;

@Data
public class CreateRegistrationRequest {
    private String contactEmailAddress;
    private String userName;
    private String userEmailAddress;
    private String userPassword;
    private String arkWalletAddress;
    private String recaptchaCode;
    private Boolean agreeToTerms;
}
