package com.arkaces.aces_marketplace_api.contract;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class Contract {
    private String id;
    private String createdAt;
    private String status;
    private String correlationId;
    private JsonNode results;
    private String serviceId;
}
