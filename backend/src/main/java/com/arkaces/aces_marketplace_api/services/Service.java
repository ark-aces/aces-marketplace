package com.arkaces.aces_marketplace_api.services;

import lombok.Data;

@Data
public class Service {
    private String id;
    private String url;
    private String name;
    private String version;
    private String description;
    private String websiteUrl;
    private String createdAt;
}
