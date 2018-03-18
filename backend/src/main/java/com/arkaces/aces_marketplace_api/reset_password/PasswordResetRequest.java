package com.arkaces.aces_marketplace_api.reset_password;

import lombok.Data;

@Data
public class PasswordResetRequest {
    public String code;
    public String password;
}
