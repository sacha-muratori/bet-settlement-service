package com.bookmaker.kafka.consumer;

import com.bookmaker.model.dto.EventOutcome;
import com.bookmaker.service.SettlementService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventConsumer {

    private final Logger log = LogManager.getLogger(getClass());

    @Autowired
    private SettlementService settlementService;

    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(EventOutcome eventOutcome) {
        log.debug("Received Event Outcome with id = {}", eventOutcome.getEventId());
        settlementService.processEventOutcome(eventOutcome);
    }
}
