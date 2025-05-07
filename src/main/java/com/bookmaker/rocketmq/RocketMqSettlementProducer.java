package com.bookmaker.rocketmq;

import com.bookmaker.model.entity.Bet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * RocketMQ producer responsible for sending Bet entities to the configured Rocket MQ topic for settlement processing.
 */
@Component
public class RocketMqSettlementProducer {

    private final Logger log = LogManager.getLogger(getClass());

    @Value("${rocketmq.topic}")
    private String topic;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    // Real Scenario, sending a message to the RocketMQ topic
    public void send(Bet bet) {
        log.debug("Sending Bet with id = {} to RocketMQ Topic = {}", bet.getId(), topic);
        rocketMQTemplate.convertAndSend(topic, bet);
    }
}
