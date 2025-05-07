package com.bookmaker.service;

import com.bookmaker.model.dto.EventOutcome;
import com.bookmaker.model.entity.Bet;
import com.bookmaker.repository.BetRepository;
import com.bookmaker.rocketmq.RocketMqSettlementProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SettlementServiceTest {

    @Mock
    private BetRepository betRepository;

    @Mock
    private RocketMqSettlementProducer rocketMqSettlementProducer;

    @InjectMocks
    private SettlementService settlementService;

    @Test
    void shouldProcessEventOutcomeAndSendBetsToRocketMq() {
        // Given
        EventOutcome eventOutcome = new EventOutcome();
        eventOutcome.setEventId(10L);

        Bet bet1 = new Bet();
        Bet bet2 = new Bet();
        List<Bet> bets = List.of(bet1, bet2);

        when(betRepository.findByEventId(10L)).thenReturn(bets);

        // When
        settlementService.processEventOutcome(eventOutcome);

        // Then
        verify(betRepository).findByEventId(10L);
        verify(rocketMqSettlementProducer).send(bet1);
        verify(rocketMqSettlementProducer).send(bet2);
    }
}
