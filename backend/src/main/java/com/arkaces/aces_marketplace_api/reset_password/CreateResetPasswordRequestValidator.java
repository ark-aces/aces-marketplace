package com.arkaces.aces_marketplace_api.reset_password;

import com.arkaces.aces_marketplace_api.error.FieldErrorCodes;
import com.arkaces.aces_marketplace_api.error.ValidatorException;
import com.arkaces.aces_marketplace_api.recaptcha.RecaptchaService;
import com.arkaces.aces_marketplace_api.user.UserEntity;
import com.arkaces.aces_marketplace_api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CreateResetPasswordRequestValidator {
    
    private final EmailValidator emailValidator;
    private final RecaptchaService recaptchaService;
    private final UserRepository userRepository;
    
    public void validate(CreateResetPasswordRequest createResetPasswordRequest, String clientIp) {
        BindingResult bindingResult = new BeanPropertyBindingResult(createResetPasswordRequest, "createResetPasswordRequest");

        String emailAddress = createResetPasswordRequest.getEmailAddress();
        if (StringUtils.isEmpty(emailAddress)) {
            bindingResult.rejectValue("emailAddress", FieldErrorCodes.REQUIRED, "Email address required.");
        } else if (! emailValidator.isValid(emailAddress)) {
            bindingResult.rejectValue("emailAddress", FieldErrorCodes.INVALID_EMAIL_ADDRESS, "Invalid email address");
        } else {
            UserEntity userEntity = userRepository.findOneByEmailAddress(emailAddress);
            if (userEntity == null) {
                bindingResult.rejectValue("emailAddress", FieldErrorCodes.USER_NOT_FOUND_WITH_EMAIL_ADDRESS, 
                    "User not found with the given email address");
            }
        }
        
        if (StringUtils.isEmpty(createResetPasswordRequest.getRecaptchaCode())) {
            bindingResult.rejectValue("recaptchaCode", FieldErrorCodes.REQUIRED, "Recaptcha code required.");
        } else {
            // validate captcha code
            String recaptchaCode = createResetPasswordRequest.getRecaptchaCode();
            if (! recaptchaService.isValid(recaptchaCode, clientIp)) {
                bindingResult.rejectValue("recaptchaCode", FieldErrorCodes.INVALID_RECAPTCHA_CODE, "Recaptcha code invalid.");
            }
        }

        if (bindingResult.hasErrors()) {
            throw new ValidatorException(bindingResult);
        }
    }
}
