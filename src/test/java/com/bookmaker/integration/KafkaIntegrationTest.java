package com.bookmaker.integration;

import com.bookmaker.model.dto.EventOutcome;
import com.bookmaker.rocketmq.RocketMqSettlementProducer;
import com.bookmaker.service.SettlementService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = {
                "listeners=PLAINTEXT://localhost:9092",
                "port=9092"
        }
)
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
})
class KafkaIntegrationTest {

    private final Logger log = LogManager.getLogger(getClass());

    @Autowired
    private KafkaTemplate<String, EventOutcome> kafkaTemplate;

    @Autowired
    private SettlementService settlementService;

    @Autowired
    private KafkaListenerEndpointRegistry registry;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    @SpyBean
    private RocketMqSettlementProducer rocketMqSettlementProducer;

    @Value("${spring.kafka.topic}")
    private String topic;

    @BeforeEach
    void waitForListenerAssignment() {
        registry.getListenerContainers().forEach(container ->
                ContainerTestUtils.waitForAssignment(container, embeddedKafka.getPartitionsPerTopic()));
    }

    @Test
    void shouldProcessEventOutcomeFromKafka() throws InterruptedException {
        EventOutcome event = new EventOutcome();
        event.setEventId(1001L);
        event.setEventName("Soccer Match Final");
        event.setEventWinnerId(501L);

        log.debug("About to send EventOutcome with id = {}, to kafka topic = {}", event.getEventId(), topic);

        Thread.sleep(5000); // give Kafka/ZooKeeper time to settle
        kafkaTemplate.send(topic, event);

        // Awaitility is good for waiting async processing
        await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
            verify(rocketMqSettlementProducer, atLeastOnce()).send(any());
        });
    }
}
