package com.arkaces.aces_marketplace_api.user;

import com.arkaces.aces_marketplace_api.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    
    @GetMapping("/user")
    public User getUser(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        UserEntity userEntity = userRepository.findOne(authenticatedUser.getUserPid());
        
        return modelMapper.map(userEntity, User.class);
    }
}
