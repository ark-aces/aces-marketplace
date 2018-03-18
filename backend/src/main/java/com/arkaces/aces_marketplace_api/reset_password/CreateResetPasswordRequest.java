package com.arkaces.aces_marketplace_api.reset_password;

import lombok.Data;

@Data
public class CreateResetPasswordRequest {
    private String emailAddress;
    private String recaptchaCode;
}
