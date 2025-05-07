package com.bookmaker.service;

import com.bookmaker.model.dto.EventOutcome;
import com.bookmaker.model.entity.Bet;
import com.bookmaker.repository.BetRepository;
import com.bookmaker.rocketmq.RocketMqSettlementProducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for processing event outcomes by retrieving related bets
 * and sending them to RocketMQ for settlement.
 * Sits between the KafkaEventConsumer and the RocketMqSettlementProducer.
 */
@Service
public class SettlementService {

    private final Logger log = LogManager.getLogger(getClass());

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private RocketMqSettlementProducer rocketMqSettlementProducer;

    public void processEventOutcome(EventOutcome eventOutcome) {
        log.debug("Processing Event Outcome matching Bets towards the RocketMQ Producer");
        List<Bet> betsToSettle = betRepository.findByEventId(eventOutcome.getEventId());
        betsToSettle.forEach(rocketMqSettlementProducer::send);
    }
}
