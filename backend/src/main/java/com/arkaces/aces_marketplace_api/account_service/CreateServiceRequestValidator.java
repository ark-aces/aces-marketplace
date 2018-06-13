package com.arkaces.aces_marketplace_api.account_service;

import com.arkaces.aces_marketplace_api.error.FieldErrorCodes;
import com.arkaces.aces_marketplace_api.error.ValidatorException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CreateServiceRequestValidator {
    
    public void validate(CreateServiceRequest createServiceRequest) {
        BindingResult bindingResult = new BeanPropertyBindingResult(createServiceRequest, "createServiceRequest");

        if (StringUtils.isEmpty(createServiceRequest.getLabel())) {
            bindingResult.rejectValue("label", FieldErrorCodes.REQUIRED, "Label is required.");
        }

        String url = createServiceRequest.getUrl();
        if (StringUtils.isEmpty(url)) {
            bindingResult.rejectValue("url", FieldErrorCodes.REQUIRED, "Service URL is required.");
        } else if (! ResourceUtils.isUrl(url)) {
            bindingResult.rejectValue("url", FieldErrorCodes.INVALID_URL, "Service URL must be a valid URL.");
        }
        
        // todo: validate service info response
        
        if (bindingResult.hasErrors()) {
            throw new ValidatorException(bindingResult);
        }
    }
}
