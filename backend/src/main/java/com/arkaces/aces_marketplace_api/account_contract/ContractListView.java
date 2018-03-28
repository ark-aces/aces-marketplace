package com.arkaces.aces_marketplace_api.account_contract;

import lombok.Data;

@Data
public class ContractListView {
    private String id;
    private String status;
    private String createdAt;
    private String serviceId;
}
