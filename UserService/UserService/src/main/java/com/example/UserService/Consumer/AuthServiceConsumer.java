package com.example.UserService.Consumer;

import com.example.UserService.Entity.UserInfo;
import com.example.UserService.Entity.UserInfoDto;
import com.example.UserService.Repository.UserRepository;
import com.example.UserService.Services.UserService;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceConsumer {


    @Autowired
    private UserService userService;

    @KafkaListener(topics = "${spring.kafka.topic.name}" , groupId = "${spring.kafka.consumer.group-id}")
    public void listen(UserInfoDto eventData){
        try{
             userService.createorupdateUser(eventData);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
