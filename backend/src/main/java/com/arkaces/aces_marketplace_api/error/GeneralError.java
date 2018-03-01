package com.arkaces.aces_marketplace_api.error;

import lombok.Data;

@Data
public class GeneralError {
    private String code;
    private String message;
}
