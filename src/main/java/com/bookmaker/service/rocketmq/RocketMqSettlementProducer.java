package com.bookmaker.service.rocketmq;

import com.bookmaker.model.Bet;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RocketMqSettlementProducer {

    @Value("${rocketmq.topic}")
    private String topic;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    // Real Scenario, sending a message to the RocketMQ
    public void send(Bet bet) {
        System.out.println("Settling bet towards Rocket MQ.. " + bet.getId());
        rocketMQTemplate.convertAndSend(topic, bet);
        System.out.println("Finished settling bet towards Rocket MQ.. " + bet.getId());
    }
}
