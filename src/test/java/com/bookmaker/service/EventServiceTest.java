package com.bookmaker.service;

import com.bookmaker.kafka.producer.KafkaEventProducer;
import com.bookmaker.model.dto.EventOutcome;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private KafkaEventProducer kafkaEventProducer;

    @InjectMocks
    private EventService eventService;

    @Test
    void shouldSendEventOutcomeToKafkaProducer() {
        // Given
        EventOutcome eventOutcome = new EventOutcome();
        eventOutcome.setEventId(100L);
        eventOutcome.setEventName("AWAY_WIN");
        eventOutcome.setEventWinnerId(200L);

        // When
        eventService.sendEventOutcome(eventOutcome);

        // Then
        verify(kafkaEventProducer).sendEventOutcome(eventOutcome);
    }
}
