package com.arkaces.aces_marketplace_api.error;

import lombok.Data;

@Data
public class FieldError {
    private String field;
    private String code;
    private String message;
}
