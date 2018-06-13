package com.arkaces.aces_marketplace_api.contract;

import lombok.Data;

import java.util.Map;

@Data
public class CreateContractRequest {
    private String label;
    private Map<String, Object> arguments;
}
