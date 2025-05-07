package com.bookmaker.kafka.producer;

import com.bookmaker.model.dto.EventOutcome;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.concurrent.SettableListenableFuture;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaEventProducerTest {

    @Mock
    private KafkaTemplate<String, EventOutcome> kafkaTemplate;

    @InjectMocks
    private KafkaEventProducer kafkaEventProducer;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(kafkaEventProducer, "topic", "test-topic");
    }

    @Test
    void shouldSendEventOutcomeSuccessfully() throws Exception {
        // Given
        EventOutcome eventOutcome = new EventOutcome();
        eventOutcome.setEventId(42L);

        SettableListenableFuture<SendResult<String, EventOutcome>> future = new SettableListenableFuture<>();
        RecordMetadata metadata = new RecordMetadata(new TopicPartition("test-topic", 0), 0, 0, 0L, 0, 0);
        ProducerRecord<String, EventOutcome> producerRecord = new ProducerRecord<>("test-topic", eventOutcome);
        SendResult<String, EventOutcome> sendResult = new SendResult<>(producerRecord, metadata);
        future.set(sendResult);

        when(kafkaTemplate.send(eq("test-topic"), eq(eventOutcome))).thenReturn(future);

        // When
        kafkaEventProducer.sendEventOutcome(eventOutcome);

        // Then
        verify(kafkaTemplate).send(eq("test-topic"), eq(eventOutcome));
    }

    @Test
    void shouldThrowKafkaExceptionOnFailure() {
        // Given
        EventOutcome eventOutcome = new EventOutcome();
        eventOutcome.setEventId(99L);

        SettableListenableFuture<SendResult<String, EventOutcome>> future = new SettableListenableFuture<>();
        future.setException(new ExecutionException("Kafka error", new RuntimeException("Cause")));

        when(kafkaTemplate.send(eq("test-topic"), eq(eventOutcome))).thenReturn(future);
        ReflectionTestUtils.setField(kafkaEventProducer, "topic", "test-topic");

        // Then
        KafkaException exception = assertThrows(KafkaException.class, () ->
                kafkaEventProducer.sendEventOutcome(eventOutcome));

        assertTrue(exception.getMessage().contains("Failed to send event to Kafka"));
        verify(kafkaTemplate).send(eq("test-topic"), eq(eventOutcome));
    }
}
