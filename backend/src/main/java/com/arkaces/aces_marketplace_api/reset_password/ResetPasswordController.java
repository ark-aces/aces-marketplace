package com.arkaces.aces_marketplace_api.reset_password;

import com.arkaces.aces_marketplace_api.common.CodeGenerator;
import com.arkaces.aces_marketplace_api.error.ErrorCodes;
import com.arkaces.aces_marketplace_api.error.NotFoundException;
import com.arkaces.aces_marketplace_api.user.UserEntity;
import com.arkaces.aces_marketplace_api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ResetPasswordController {

    private final UserRepository userRepository;
    private final CreateResetPasswordRequestValidator createResetPasswordRequestValidator;
    private final CodeGenerator codeGenerator;
    private final UserResetPasswordRequestRepository userResetPasswordRequestRepository;
    private final ResetPasswordMailerService resetPasswordMailerService;
    private final PasswordResetService passwordResetService;
    
    @PostMapping("/resetPasswordRequests")
    @ResponseStatus(value = HttpStatus.OK)
    public CreateResetPasswordRequest createResetPasswordRequest(
        @RequestBody CreateResetPasswordRequest createResetPasswordRequest,
        HttpServletRequest httpServletRequest    
    ) {
        String clientIp = httpServletRequest.getHeader("X-FORWARDED-FOR");
        if (clientIp == null || "".equals(clientIp)) {
            clientIp = httpServletRequest.getRemoteAddr();
        }
        createResetPasswordRequestValidator.validate(createResetPasswordRequest, clientIp);

        UserEntity userEntity = userRepository.findOneByEmailAddress(createResetPasswordRequest.getEmailAddress());
        if (userEntity == null) {
            throw new NotFoundException(ErrorCodes.NOT_FOUND, "User not found");
        }
        
        String code = codeGenerator.generate(20);

        UserResetPasswordRequestEntity userResetPasswordRequestEntity = new UserResetPasswordRequestEntity();
        userResetPasswordRequestEntity.setCode(code);
        userResetPasswordRequestEntity.setIsUsed(false);
        userResetPasswordRequestEntity.setCreatedAt(LocalDateTime.now());
        userResetPasswordRequestEntity.setUserEntity(userEntity);
        userResetPasswordRequestRepository.save(userResetPasswordRequestEntity);

        resetPasswordMailerService.sendResetPasswordLink(userEntity.getName(), userEntity.getEmailAddress(), code);
        
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setCreatedAt(LocalDateTime.now().atOffset(ZoneOffset.UTC).toString());

        return createResetPasswordRequest;
    }
    
    @PostMapping("/passwordResets")
    public PasswordReset createPasswordReset(@RequestBody PasswordResetRequest passwordResetRequest) {
        passwordResetService.reset(passwordResetRequest);

        PasswordReset passwordReset = new PasswordReset();
        passwordReset.setCreatedAt(LocalDateTime.now().atOffset(ZoneOffset.UTC).toString());
        
        return passwordReset;
    }
}
