package com.arkaces.aces_marketplace_api.registration;

import lombok.Data;

@Data
public class User {
    private String id;
    private String name;
    private String emailAddress;
    private Boolean isEmailAddressVerified;
}
