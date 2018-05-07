package com.arkaces.aces_marketplace_api.services;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceSearchCriteria {

    private String search;
    private List<String> categories;
    private BigDecimal maxFlatFee;
    private BigDecimal maxPercentFee;
    private List<String> minCapacities;
}
