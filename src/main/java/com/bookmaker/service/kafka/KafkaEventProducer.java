package com.bookmaker.service.kafka;

import com.bookmaker.model.EventOutcome;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.stereotype.Component;

@Component
@EnableKafka
public class KafkaEventProducer {

    @Value("${spring.kafka.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, EventOutcome> kafkaTemplate;

    public void sendEventOutcome(EventOutcome eventOutcome) {

        System.out.println("Event sent!!!!!!");

        kafkaTemplate.send(topic, eventOutcome);
    }
}
