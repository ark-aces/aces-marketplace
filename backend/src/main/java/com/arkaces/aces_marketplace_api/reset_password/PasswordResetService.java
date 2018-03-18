package com.arkaces.aces_marketplace_api.reset_password;

import com.arkaces.aces_marketplace_api.error.ErrorCodes;
import com.arkaces.aces_marketplace_api.error.NotFoundException;
import com.arkaces.aces_marketplace_api.user.UserEntity;
import com.arkaces.aces_marketplace_api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PasswordResetService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserResetPasswordRequestRepository userResetPasswordRequestRepository;
    private final UserRepository userRepository;

    public void reset(PasswordResetRequest passwordResetRequest) {
        UserResetPasswordRequestEntity userResetPasswordRequestEntity = userResetPasswordRequestRepository
            .findOneByCode(passwordResetRequest.getCode());
        if (userResetPasswordRequestEntity == null) {
            throw new NotFoundException(ErrorCodes.NOT_FOUND, "Reset code not found");
        }
        userResetPasswordRequestEntity.setIsUsed(true);
        userResetPasswordRequestRepository.save(userResetPasswordRequestEntity);


        String passwordHash = bCryptPasswordEncoder.encode(passwordResetRequest.getPassword());

        UserEntity userEntity = userResetPasswordRequestEntity.getUserEntity();
        userEntity.setPasswordHash(passwordHash);
        userRepository.save(userEntity);
    }

}
