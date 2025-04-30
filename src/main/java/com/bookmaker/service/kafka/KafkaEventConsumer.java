package com.bookmaker.service.kafka;

import com.bookmaker.model.EventOutcome;
import com.bookmaker.repository.BetRepository;
import com.bookmaker.model.Bet;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaEventConsumer {

    @Autowired
    private BetRepository betRepository;

    @KafkaListener(topics = "event-outcomes", groupId = "dev_group")
    public void consume(EventOutcome eventOutcome) {

        System.out.println("Event received!!!!!!");

        //TODO work on h2 in-memory db and rocket mq
//        // Logic to match event outcomes with bets in the database
//        List<Bet> betsToSettle = betRepository.findByEventId(eventOutcome.getEventId());
//
//        for (Bet bet : betsToSettle) {
//            if (bet.getEventWinnerId().equals(eventOutcome.getEventWinnerId())) {
//                // Send bet settlement to RocketMQ (mocked here)
//                // In real setup, you'd use RocketMQProducer here
//                System.out.println("Bet settled for Bet ID: " + bet.getBetId());
//            }
//        }
    }
}
