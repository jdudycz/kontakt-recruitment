package io.kontakt.apps.thermometer.simulator.publishing;

import io.kontakt.apps.event.TemperatureReading;
import io.kontakt.apps.thermometer.simulator.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.Instant;

public class TemperatureStreamIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private TemperatureStreamPublisher publisher;

    @Test
    @DisplayName("Should publish temperature reading to kafka broker")
    void testRecordPublishing() {
        try (var consumer = createKafkaConsumer()) {
            // given
            var temperatureReading = new TemperatureReading("thermometer", "room", 20, Instant.now());

            // when
            publisher.publish(temperatureReading);

            // then
            consumer.drain(
                    consumerRecords -> consumerRecords.stream()
                            .anyMatch(r -> r.value().thermometerId().equals(temperatureReading.thermometerId())),
                    Duration.ofSeconds(5)
            );
        }
    }
}
