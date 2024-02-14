package io.kontakt.apps.anomaly.detector;

import io.kontakt.apps.anomaly.detector.kafka.TestKafkaConsumer;
import io.kontakt.apps.anomaly.detector.kafka.TestKafkaProducer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


@Testcontainers
@SpringBootTest(classes = {AnomalyDetectorApplication.class})
public class AbstractIntegrationTest {

    @Container
    public final static KafkaContainer kafkaContainer =
            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.0"));

    static {
        kafkaContainer.start();
    }

    @DynamicPropertySource
    static void datasourceConfig(DynamicPropertyRegistry registry) {
        registry.add("spring.cloud.stream.kafka.binder.brokers", kafkaContainer::getBootstrapServers);
    }

    protected <T> TestKafkaConsumer<T> createKafkaConsumer(String topic, Class<T> tClass) {
        return new TestKafkaConsumer<>(kafkaContainer.getBootstrapServers(), topic, tClass);
    }

    protected <T> TestKafkaProducer<T> createKafkaProducer(String topic) {
        return new TestKafkaProducer<>(kafkaContainer.getBootstrapServers(), topic);
    }
}
