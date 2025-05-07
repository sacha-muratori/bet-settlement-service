package com.bookmaker.rocketmq;

import com.bookmaker.model.entity.Bet;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RocketMqSettlementProducerTest {

    @Mock
    private RocketMQTemplate rocketMQTemplate;

    @InjectMocks
    private RocketMqSettlementProducer rocketMqSettlementProducer;

    @Test
    void shouldSendBetToRocketMq() {
        // Given
        String topic = "test-topic";
        Bet bet = new Bet();
        bet.setId(42L);

        // Inject topic manually since @Value isn't used in tests
        ReflectionTestUtils.setField(rocketMqSettlementProducer, "topic", topic);

        // When
        rocketMqSettlementProducer.send(bet);

        // Then
        verify(rocketMQTemplate).convertAndSend(topic, bet);
    }
}
