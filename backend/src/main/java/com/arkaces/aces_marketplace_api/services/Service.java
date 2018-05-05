package com.arkaces.aces_marketplace_api.services;

import com.arkaces.aces_marketplace_api.service_category.ServiceCategory;
import com.arkaces.aces_marketplace_api.service_client.Capacity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Service {
    private String id;
    private String url;
    private String name;
    private String version;
    private String description;
    private String websiteUrl;
    private String createdAt;
    private Boolean isTestnet;
    private List<Capacity> capacities;
    private BigDecimal flatFee;
    private BigDecimal percentFee;
    private List<ServiceCategory> serviceCategories;
}
