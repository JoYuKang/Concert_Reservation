package kr.hhplus.be.server.payment.application.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Testcontainers
public class PaymentKafkaTest {

    @Container
    static final KafkaContainer kafka = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:latest")
    ).withExposedPorts(9093);  // 기본 카프카 포트를 노출하도록 설정

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private String receivedMessage;

    // 카프카 메시지 리스너
    @KafkaListener(topics = "payment-events", groupId = "test-group")
    public void listen(String message) {
        log.info("Kafka에서 수신한 메시지: {}", message);
        receivedMessage = message;
    }

    @BeforeAll
    static void createTopic() throws Exception {
        // Kafka AdminClient를 사용하여 토픽 생성
        Properties props = new Properties();
        props.put("bootstrap.servers", kafka.getBootstrapServers());

        try (AdminClient adminClient = AdminClient.create(props)) {
            NewTopic topic = new NewTopic("payment-events", 1, (short) 1);
            adminClient.createTopics(Collections.singletonList(topic)).all().get();
        }
    }

    @Test
    @DisplayName("결제 메시지가 성공적으로 발송된다.")
    void testKafkaMessageSendAndReceive() {
        // Given
        String eventMemberName = "john";
        String eventType = "concert";
        LocalDateTime eventTime = LocalDateTime.now();

        String formattedEventTime = eventTime.format(DateTimeFormatter.ISO_DATE_TIME);

        // 메시지 문자열 생성
        String message = String.format("{\"eventMemberName\":\"%s\",\"eventType\":\"%s\",\"eventTime\":\"%s\"}",
                eventMemberName, eventType, formattedEventTime);

        // When
        kafkaTemplate.send("payment-events", message);

        // Then
        await().atMost(5, SECONDS)
                .pollInterval(Duration.ofMillis(300))
                .untilAsserted(() -> {
                    log.info("수신 메시지: {}", receivedMessage);
                    assertThat(receivedMessage).isEqualTo(message);
                });
        log.info("Kafka 메시지가 정상적으로 송수신되었습니다.");
    }

}
