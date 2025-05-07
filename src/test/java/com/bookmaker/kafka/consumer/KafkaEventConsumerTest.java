package com.bookmaker.kafka.consumer;

import com.bookmaker.model.dto.EventOutcome;
import com.bookmaker.service.SettlementService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class KafkaEventConsumerTest {

    @Mock
    private SettlementService settlementService;

    @InjectMocks
    private KafkaEventConsumer kafkaEventConsumer;

    @Test
    void shouldConsumeEventAndCallSettlementService() {
        // Given
        EventOutcome eventOutcome = new EventOutcome();
        eventOutcome.setEventId(42L);

        // When
        kafkaEventConsumer.consume(eventOutcome);

        // Then
        verify(settlementService).processEventOutcome(eventOutcome);
    }
}
