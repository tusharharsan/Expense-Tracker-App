package org.example.EventProducer;



import org.example.model.UsereEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoProducer {

    private KafkaTemplate<String , UsereEntityDto> kafkaTemplate;

    @Value("${spring.kafka.topic-json.name}")
    private String TOPIC_NAME;

    @Autowired
    UserInfoProducer(KafkaTemplate<String , UsereEntityDto> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEventoKafka(UserInfoEvent user){
        Message<UserInfoEvent> message = MessageBuilder.withPayload(user).setHeader(KafkaHeaders.TOPIC , TOPIC_NAME).build();


        kafkaTemplate.send(message);
    }

}
