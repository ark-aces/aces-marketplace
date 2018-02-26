package com.arkaces.aces_marketplace_api.registration;

import com.arkaces.aces_marketplace_api.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationMapper {
    
    private final ModelMapper modelMapper;
    
    public Registration map(RegistrationEntity registrationEntity) {
        List<User> users = new ArrayList<>();
        for (UserEntity userEntity : registrationEntity.getAccountEntity().getUserEntities()) {
            users.add(modelMapper.map(userEntity, User.class));
        }
        
        Account account = modelMapper.map(registrationEntity.getAccountEntity(), Account.class);
        account.setUsers(users);
        
        Registration registration = new Registration();
        modelMapper.map(registrationEntity, registration);
        registration.setAccount(account);
        
        return registration;        
    }
}
