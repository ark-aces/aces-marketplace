package com.arkaces.aces_marketplace_api.registration;

import lombok.Data;

import java.util.List;

@Data
public class Account {
    private String id;
    private String contactEmailAddress;
    private Boolean isContactEmailAddressVerified;
    private String arkWalletAddress;
    private String createdAt;
    private List<User> users;
}
