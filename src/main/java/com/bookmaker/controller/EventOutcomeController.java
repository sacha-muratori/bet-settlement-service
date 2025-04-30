package com.bookmaker.controller;

import com.bookmaker.model.EventOutcome;
import com.bookmaker.service.kafka.KafkaEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventOutcomeController {

    @Autowired
    private KafkaEventProducer kafkaEventProducer;

    @PostMapping("/publish")
    public String publishEventOutcome(@RequestBody EventOutcome eventOutcome) {
        kafkaEventProducer.sendEventOutcome(eventOutcome);
        return "Event Outcome Published";
    }
}
