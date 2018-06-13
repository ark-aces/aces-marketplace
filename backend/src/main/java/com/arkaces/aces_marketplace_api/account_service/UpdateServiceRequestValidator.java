package com.arkaces.aces_marketplace_api.account_service;

import com.arkaces.aces_marketplace_api.error.FieldErrorCodes;
import com.arkaces.aces_marketplace_api.error.ValidatorException;
import com.arkaces.aces_marketplace_api.services.ServiceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UpdateServiceRequestValidator {
    
    private final List<String> allowedServiceStatuses = Arrays.asList(
        ServiceStatus.ACTIVE,
        ServiceStatus.INACTIVE,
        ServiceStatus.DELETED
    );
    
    public void validate(UpdateServiceRequest updateServiceRequest) {
        BindingResult bindingResult = new BeanPropertyBindingResult(updateServiceRequest, "updateServiceRequest");

        if (updateServiceRequest.getLabel() != null) {
            if (StringUtils.isEmpty(updateServiceRequest.getLabel())) {
                bindingResult.rejectValue("label", FieldErrorCodes.REQUIRED, "Label is required.");
            }
        }

        String url = updateServiceRequest.getUrl();
        if (url != null) {
            if (StringUtils.isEmpty(url)) {
                bindingResult.rejectValue("url", FieldErrorCodes.REQUIRED, "Service URL is required.");
            } else if (! ResourceUtils.isUrl(url)) {
                bindingResult.rejectValue("url", FieldErrorCodes.INVALID_URL, "Service URL must be a valid URL.");
            }
        }

        String status = updateServiceRequest.getStatus();
        if (status != null) {
            if (! allowedServiceStatuses.contains(status)) {
                bindingResult.rejectValue("status", FieldErrorCodes.INVALID_SERVICE_STATUS, "Service status is invalid.");
            }
        }
        
        // todo: validate service info response

        if (bindingResult.hasErrors()) {
            throw new ValidatorException(bindingResult);
        }
    }
}
