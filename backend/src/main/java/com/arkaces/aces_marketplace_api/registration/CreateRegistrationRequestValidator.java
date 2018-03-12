package com.arkaces.aces_marketplace_api.registration;

import com.arkaces.aces_marketplace_api.error.FieldErrorCodes;
import com.arkaces.aces_marketplace_api.error.ValidatorException;
import com.arkaces.aces_marketplace_api.recaptcha.RecaptchaService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;

import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Base58;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CreateRegistrationRequestValidator {
    
    private final EmailValidator emailValidator;
    private final RecaptchaService recaptchaService;
    
    private static final Integer minUserPasswordLength = 8;

    public void validate(CreateRegistrationRequest createRegistrationRequest, String clientIp) {
        BindingResult bindingResult = new BeanPropertyBindingResult(createRegistrationRequest, "createRegistrationRequest");

        String contactEmailAddress = createRegistrationRequest.getContactEmailAddress();
        if (StringUtils.isEmpty(contactEmailAddress)) {
            bindingResult.rejectValue("contactEmailAddress", FieldErrorCodes.REQUIRED, "Email address required.");
        } else {
            if (! emailValidator.isValid(contactEmailAddress)) {
                bindingResult.rejectValue("contactEmailAddress", FieldErrorCodes.INVALID_EMAIL_ADDRESS, "Invalid email address");
            }
        }
        
        String userEmailAddress = createRegistrationRequest.getUserEmailAddress();
        if (StringUtils.isEmpty(userEmailAddress)) {
            bindingResult.rejectValue("userEmailAddress", FieldErrorCodes.REQUIRED, "Email address required.");
        } else {
            if (! emailValidator.isValid(userEmailAddress)) {
                bindingResult.rejectValue("userEmailAddress", FieldErrorCodes.INVALID_EMAIL_ADDRESS, "Invalid email address");
            }
        }

        String userName = createRegistrationRequest.getUserName();
        if (StringUtils.isEmpty(userName)) {
            bindingResult.rejectValue("userName", FieldErrorCodes.REQUIRED, "User name required.");
        }

        String userPassword = createRegistrationRequest.getUserPassword();
        if (StringUtils.isEmpty(userPassword)) {
            bindingResult.rejectValue("userPassword", FieldErrorCodes.REQUIRED, "User password required.");
        } else {
            if (userPassword.length() < minUserPasswordLength) {
                Object[] errorArgs = new Object[1];
                errorArgs[0] = minUserPasswordLength;
                bindingResult.rejectValue(
                    "userPassword", 
                    FieldErrorCodes.MIN_LENGTH_REQUIRED, 
                    errorArgs, 
                    "User password min length required."
                );
            }
        }

        String arkWalletAddress = createRegistrationRequest.getArkWalletAddress();
        if (arkWalletAddress != null) {
            try {
                Base58.decodeChecked(arkWalletAddress);
            } catch (AddressFormatException exception) {
                if (exception.getMessage().equals("Checksum does not validate")) {
                    bindingResult.rejectValue(
                        "arkWalletAddress",
                        FieldErrorCodes.INVALID_ARK_ADDRESS_CHECKSUM,
                        "Invalid Ark address checksum."
                    );
                } else {
                    bindingResult.rejectValue(
                        "arkWalletAddress",
                        FieldErrorCodes.INVALID_ARK_ADDRESS,
                        "Invalid Ark address."
                    );
                }

            }
        }

        if (StringUtils.isEmpty(createRegistrationRequest.getRecaptchaCode())) {
            bindingResult.rejectValue("recaptchaCode", FieldErrorCodes.REQUIRED, "Recaptcha code required.");
        } else {
            // validate captcha code
            String recaptchaCode = createRegistrationRequest.getRecaptchaCode();
            if (! recaptchaService.isValid(recaptchaCode, clientIp)) {
                bindingResult.rejectValue("recaptchaCode", FieldErrorCodes.INVALID_RECAPTCHA_CODE, "Recaptcha code invalid.");
            }
        }

        if (createRegistrationRequest.getAgreeToTerms() == null) {
            bindingResult.rejectValue("agreeToTerms", FieldErrorCodes.REQUIRED, "Agree to terms value required.");
        } else {
            if (! createRegistrationRequest.getAgreeToTerms()) {
                bindingResult.rejectValue("agreeToTerms", FieldErrorCodes.MUST_AGREE_TO_TERMS, "Must agree to terms.");
            }
        }

        if (bindingResult.hasErrors()) {
            throw new ValidatorException(bindingResult);
        }
    }
}
