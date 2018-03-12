package com.arkaces.aces_marketplace_api.error;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ValidationException extends RuntimeException {

    private String code;
    private String message;
    private List<FieldError> fieldErrors;

    public ValidationException(String code, String message) {
        this(code, message, null, null);
    }

    public ValidationException(String code, String message, Exception cause) {
        this(code, message, null, cause);
    }

    public ValidationException(String code, String message, List<FieldError> fieldErrors, Exception cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }
}
