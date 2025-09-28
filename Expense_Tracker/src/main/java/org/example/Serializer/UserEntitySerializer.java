package org.example.Serializer;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.example.EventProducer.UserInfoEvent;
import org.example.model.UsereEntityDto;

import java.util.Map;

public class UserEntitySerializer implements Serializer<UserInfoEvent> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, UserInfoEvent usereEntityDto) {
        byte[] reval = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            reval = mapper.writeValueAsString(usereEntityDto).getBytes();
        }catch (Exception e){
            e.printStackTrace();
        }

        return reval;
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
