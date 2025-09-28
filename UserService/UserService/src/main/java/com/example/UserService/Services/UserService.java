package com.example.UserService.Services;


import com.example.UserService.Entity.UserInfo;
import com.example.UserService.Entity.UserInfoDto;
import com.example.UserService.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserInfoDto createorupdateUser(UserInfoDto userInfoDto){
        Function<UserInfo, UserInfo> updatingUser = user ->{
            user.setUserId(userInfoDto.getUserId());
            user.setUsername(userInfoDto.getUsername());
            user.setLastname(userInfoDto.getLastName());
            user.setEmail(userInfoDto.getEmail());
            user.setPhoneNumber(userInfoDto.getPhoneNumber());
            user.setProfilePic(userInfoDto.getProfilePic() );
            return userRepository.save( userInfoDto.transformtouseringo());
        };

        Supplier<UserInfo> createUser = () ->{
            return userRepository.save(userInfoDto.transformtouseringo());
        };

        UserInfo userInfo = userRepository.findByUserId(userInfoDto.getUserId())
                .map(updatingUser)
                .orElseGet(createUser);


        return UserInfoDto.builder()
                .userId(userInfo.getUserId())
                .username(userInfo.getUsername())
                .lastName(userInfo.getLastname())
                .phoneNumber(userInfo.getPhoneNumber())
                .email(userInfo.getEmail())
                .profilePic(userInfo.getProfilePic())
                .build();
    }



    public UserInfoDto getUser(UserInfoDto userInfoDto) throws Exception{
        Optional<UserInfo> userinfoopt = userRepository.findByUserId(userInfoDto.getUserId());

        if(userinfoopt.isEmpty()){
            throw new Exception("User not found!");
        }

        UserInfo userInfo = userinfoopt.get();

        return UserInfoDto.builder()
                .userId(userInfo.getUserId())
                .username(userInfo.getUsername())
                .lastName(userInfo.getLastname())
                .phoneNumber(userInfo.getPhoneNumber())
                .email(userInfo.getEmail())
                .profilePic(userInfo.getProfilePic())
                .build();
    }
}
