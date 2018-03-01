package com.arkaces.aces_marketplace_api.service_client;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Capacity {
    private String unit;
    private BigDecimal value;
    private String displayValue;
}