package com.arkaces.aces_marketplace_api.account_service;

import lombok.Data;

import java.util.Set;

@Data
public class UpdateServiceRequest {
    private String label;
    private String url;
    private Set<Long> categoryPids;
    private Boolean isTestnet;
    private String status;
}
