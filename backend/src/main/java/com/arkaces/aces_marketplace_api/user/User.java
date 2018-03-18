package com.arkaces.aces_marketplace_api.user;

import lombok.Data;

@Data
public class User {
    private String id;
    private String name;
    private String emailAddress;
    private String createdAt;
}
