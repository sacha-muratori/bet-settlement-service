package com.bookmaker.service;

import com.bookmaker.kafka.producer.KafkaEventProducer;
import com.bookmaker.model.dto.EventOutcome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final Logger log = LogManager.getLogger(getClass());

    @Autowired
    private KafkaEventProducer kafkaEventProducer;

    public void sendEventOutcome(EventOutcome eventOutcome) {
        log.debug("Sending Event Outcome to Kafka Producer");
        kafkaEventProducer.sendEventOutcome(eventOutcome);
    }
}