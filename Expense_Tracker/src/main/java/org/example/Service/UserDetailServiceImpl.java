package org.example.Service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.Entities.UsersEntity;
import org.example.EventProducer.UserInfoEvent;
import org.example.EventProducer.UserInfoProducer;
import org.example.Repository.UserRepository;
import org.example.model.UsereEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;


@Component
@Data
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserInfoProducer userInfoProducer;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersEntity user = userRepository.findByUsername(username);
        if(user == null){
            // Add more descriptive error message with the username that wasn't found
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return new CustomUserDetails(user);
    }

    public UsersEntity checkifUserAlreadyExist(UsersEntity user){
        return userRepository.findByUsername(user.getUsername());
    }

    public Boolean signup(UsereEntityDto user) throws IOException {
//
//        if(!ValidationUtils.validate(user)){
//            return false;
//        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(Objects.nonNull(checkifUserAlreadyExist(user))){
            return false;
        }

        String userId = UUID.randomUUID().toString();
        userRepository.save(new UsersEntity(userId , user.getUsername() ,user.getPassword() , new HashSet<>()));
        userInfoProducer.sendEventoKafka(userinfoeventtopublissh(user ,userId));
        return true;
    }

    private UserInfoEvent userinfoeventtopublissh(UsereEntityDto usereEntityDto , String userId){
        return UserInfoEvent.builder()
                .userId(userId)
                .username(usereEntityDto.getUsername())
                .lastname(usereEntityDto.getLastname())
                .email(usereEntityDto.getEmail())
                .phoneNumber(usereEntityDto.getPhoneNumber())
                .build();
    }
}
