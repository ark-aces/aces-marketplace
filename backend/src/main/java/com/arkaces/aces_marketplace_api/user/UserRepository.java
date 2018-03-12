package com.arkaces.aces_marketplace_api.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findOneById(String id);
    
    List<UserEntity> findAllByEmailAddressVerificationCode(String emailAddressVerificationCode);
}
