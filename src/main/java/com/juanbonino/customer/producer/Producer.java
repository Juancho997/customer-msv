package com.juanbonino.customer.producer;

import com.juanbonino.customer.producer.models.InitializationMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class Producer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${cloudkarafka.topic}")
    private String topic;

    Producer(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(InitializationMessage message){
        kafkaTemplate.send(topic, message.getMessage());
        System.out.println("Sent initialization message [" + message + "] to " + topic);
    }
}
