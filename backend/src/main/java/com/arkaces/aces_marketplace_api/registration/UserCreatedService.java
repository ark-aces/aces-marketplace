package com.arkaces.aces_marketplace_api.registration;

import com.arkaces.aces_marketplace_api.user.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserCreatedService {

    private final ApplicationEventPublisher applicationEventPublisher;
    
    public void notifyUserCreated(UserEntity userEntity) {
        UserCreatedEvent userCreatedEvent = new UserCreatedEvent();
        userCreatedEvent.setUserPid(userEntity.getPid());
        applicationEventPublisher.publishEvent(userCreatedEvent);
        log.info("Published user created event: " + userCreatedEvent);
    }
    
}
