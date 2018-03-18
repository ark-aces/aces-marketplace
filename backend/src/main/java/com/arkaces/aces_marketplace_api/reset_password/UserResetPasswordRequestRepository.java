package com.arkaces.aces_marketplace_api.reset_password;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserResetPasswordRequestRepository extends JpaRepository<UserResetPasswordRequestEntity, Long> {

    UserResetPasswordRequestEntity findOneByCode(String code);
}
