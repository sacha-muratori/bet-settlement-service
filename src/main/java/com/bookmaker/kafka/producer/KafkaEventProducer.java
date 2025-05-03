package com.bookmaker.kafka.producer;

import com.bookmaker.model.dto.EventOutcome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@EnableKafka
public class KafkaEventProducer {

    private final Logger log = LogManager.getLogger(getClass());

    @Value("${spring.kafka.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, EventOutcome> kafkaTemplate;

    public void sendEventOutcome(EventOutcome eventOutcome) {
        log.debug("Sending Event Outcome with id = {} to Kafka Topic = {}", eventOutcome.getEventId(), topic);
        try {
            kafkaTemplate.send(topic, eventOutcome).get();
        } catch (Exception e) {
            throw new KafkaException("Failed to send event to Kafka", e);
        }
    }
}
