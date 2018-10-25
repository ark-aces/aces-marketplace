package com.arkaces.aces_marketplace_api.services;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeRate {
    private String from;
    private String to;
    private BigDecimal rate;
}
