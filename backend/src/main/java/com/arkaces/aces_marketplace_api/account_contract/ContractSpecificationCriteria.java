package com.arkaces.aces_marketplace_api.account_contract;

import com.arkaces.aces_marketplace_api.account.AccountEntity;
import lombok.Data;

@Data
public class ContractSpecificationCriteria {
    private AccountEntity accountEntity;
    private String status;
}
