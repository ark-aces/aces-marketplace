package com.arkaces.aces_marketplace_api.account_service;

import com.arkaces.aces_marketplace_api.services.Service;
import lombok.Data;

@Data
public class AccountService {
    private Service service;
    private String createdAt;
}
