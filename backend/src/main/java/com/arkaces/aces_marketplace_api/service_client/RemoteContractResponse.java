package com.arkaces.aces_marketplace_api.service_client;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class RemoteContractResponse {
    private String id;
    private String createdAt;
    private String correlationId;
    private String status;
    private JsonNode results;
}
