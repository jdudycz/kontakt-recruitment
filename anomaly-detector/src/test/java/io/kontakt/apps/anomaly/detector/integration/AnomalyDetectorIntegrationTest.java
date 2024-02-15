package io.kontakt.apps.anomaly.detector.integration;

import io.kontakt.apps.event.Anomaly;
import io.kontakt.apps.anomaly.detector.AbstractIntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;
import java.util.List;

import static io.kontakt.apps.anomaly.detector.utils.Fixtures.aReading;


public class AnomalyDetectorIntegrationTest extends AbstractIntegrationTest {

    @Value("${spring.cloud.stream.bindings.anomalyDetectorProcessor-in-0.destination}")
    private String tempReadingTopic;

    @Value("${spring.cloud.stream.bindings.anomalyDetectorProcessor-out-0.destination}")
    private String anomalyTopic;

    @Test
    @DisplayName("Application should read temperature readings and check for anomalies using configured algorithm")
    void testAnomalyProcessing() {
        try (var anomalyConsumer = createKafkaConsumer(anomalyTopic, Anomaly.class);
             var tempReadingProducer = createKafkaProducer(tempReadingTopic)) {
            // given
            var readings = List.of(aReading(), aReading(), aReading(), aReading());

            // when
            readings.forEach(r -> tempReadingProducer.produce(r.thermometerId(), r));

            // then
            var detectedAnomalies = anomalyConsumer.drain(d -> true, Duration.ofSeconds(5));
            var expectedAnomalies = readings.stream().map(Anomaly::new).toList();
            // we are using AlwaysAnomalyAnomalyDetector configured in application.properties
            Assertions.assertThat(detectedAnomalies).containsAll(expectedAnomalies);
        }
    }

}
