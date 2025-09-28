package com.example.service.Consumer;

import com.example.service.Dto.ExpenseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;

public class ExpenseDeserializer implements Deserializer<ExpenseDto> {


    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public ExpenseDto deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        ExpenseDto expenseDto = null;
        try {
            expenseDto = mapper.readValue(bytes, ExpenseDto.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        return expenseDto;
    }

    @Override
    public ExpenseDto deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public ExpenseDto deserialize(String topic, Headers headers, ByteBuffer data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {

    }
}
