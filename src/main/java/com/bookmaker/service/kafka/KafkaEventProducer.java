package com.bookmaker.service.kafka;

import com.bookmaker.model.EventOutcome;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.stereotype.Component;

@Component
@EnableKafka
public class KafkaEventProducer {

    @Autowired
    private KafkaTemplate<String, EventOutcome> kafkaTemplate;

    private static final String TOPIC = "event-outcomes";

    public void sendEventOutcome(EventOutcome eventOutcome) {
        kafkaTemplate.send(TOPIC, eventOutcome);
    }
}
