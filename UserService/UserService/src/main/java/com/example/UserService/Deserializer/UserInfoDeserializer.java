package com.example.UserService.Deserializer;


import com.example.UserService.Entity.UserInfoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Objects;

public class UserInfoDeserializer implements Deserializer<UserInfoDto> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public UserInfoDto deserialize(String s, byte[] users) {
        ObjectMapper mapper = new ObjectMapper();
        UserInfoDto user = null;
        try {
            user = mapper.readValue(users , UserInfoDto.class);
        }catch (Exception e){
            System.out.println("Can not Deserialize");
        }
        return user;
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
