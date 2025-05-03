package com.bookmaker.service.kafka;

import com.bookmaker.model.EventOutcome;
import com.bookmaker.repository.BetRepository;
import com.bookmaker.model.Bet;
import com.bookmaker.service.rocketmq.RocketMqSettlementProducer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaEventConsumer {

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private RocketMqSettlementProducer rocketMqSettlementProducer;

    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(EventOutcome eventOutcome) {

        System.out.println("Event received!!!!!! id = " + eventOutcome.getEventId());

        List<Bet> betsToSettle = betRepository.findByEventId(eventOutcome.getEventId());
        for (Bet bet : betsToSettle) {
            System.out.println("Bet to Settle: " + bet.getId());

            if (bet.getEventId().equals(eventOutcome.getEventId())) {

                System.out.println("Before rocketMQSettlementProducer: " + bet.getEventId());

                // Send bet settlement to RocketMQ - Real
                rocketMqSettlementProducer.send(bet);
            }
        }
    }
}
