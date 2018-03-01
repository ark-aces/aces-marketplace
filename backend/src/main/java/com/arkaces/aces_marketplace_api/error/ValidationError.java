package com.arkaces.aces_marketplace_api.error;

import lombok.Data;

import java.util.List;

@Data
public class ValidationError {
    private String code;
    private String message;
    private List<FieldError> fieldErrors;
}
