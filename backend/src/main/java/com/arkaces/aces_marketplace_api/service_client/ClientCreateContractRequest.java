package com.arkaces.aces_marketplace_api.service_client;

import lombok.Data;
import java.util.Map;

@Data
public class ClientCreateContractRequest {
    private String correlationId;
    private Map<String, Object> arguments;
}
