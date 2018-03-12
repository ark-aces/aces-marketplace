package com.arkaces.aces_marketplace_api.error;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.validation.BindingResult;

@Data
@EqualsAndHashCode(callSuper = false)
public class ValidatorException extends RuntimeException {

    private BindingResult bindingResult;

    public ValidatorException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }
}
