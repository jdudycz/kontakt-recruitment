package io.kontakt.apps.thermometer.simulator;

import io.kontakt.apps.event.TemperatureReading;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest(classes = ThermometerSimulatorApplication.class)
public class AbstractIntegrationTest {
    public final static KafkaContainer kafkaContainer =
            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.0"));

    static {
        kafkaContainer.start();
    }

    @DynamicPropertySource
    static void datasourceConfig(DynamicPropertyRegistry registry) {
        registry.add("spring.cloud.stream.kafka.binder.brokers", kafkaContainer::getBootstrapServers);
    }

    @Value("${spring.cloud.stream.bindings.messageProducer-out-0.destination}")
    private String topic;

    protected TestKafkaConsumer<TemperatureReading> createKafkaConsumer() {
        return new TestKafkaConsumer<>(
                kafkaContainer.getBootstrapServers(),
                topic,
                TemperatureReading.class
        );
    }
}
