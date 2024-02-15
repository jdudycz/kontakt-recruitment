package io.kontakt.apps.anomaly.detector.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kontakt.apps.anomaly.detector.detection.AnomalyDetector;
import io.kontakt.apps.anomaly.detector.processing.TemperatureAnomalyProcessor;
import io.kontakt.apps.event.Anomaly;
import io.kontakt.apps.event.TemperatureReading;
import io.kontakt.apps.serialization.ObjectMapperFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;

import java.util.function.Function;


@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    @Bean
    public Function<Flux<Message<TemperatureReading>>, Flux<Message<Anomaly>>> anomalyDetectorProcessor(
            AnomalyDetector anomalyDetector
    ) {
        return new TemperatureAnomalyProcessor(anomalyDetector);
    }

    @Bean
    public static ObjectMapper objectMapper() {
        return ObjectMapperFactory.createJsonMapper();
    }
}
