package com.bookmaker.service.rocketmq;

import org.springframework.stereotype.Component;

@Component
public class RocketMqSettlementProducer {

    public void sendSettlementMessage(String settlementPayload) {
        // In a real setup, here you'd produce to RocketMQ, but for now, we'll just log
        System.out.println("Mock RocketMQ Payload: " + settlementPayload);
    }
}
