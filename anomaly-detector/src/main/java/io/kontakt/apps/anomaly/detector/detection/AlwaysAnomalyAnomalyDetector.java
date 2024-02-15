package io.kontakt.apps.anomaly.detector.detection;

import io.kontakt.apps.event.Anomaly;
import io.kontakt.apps.event.TemperatureReading;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@Component
@ConditionalOnProperty(name = "anomaly-detector.algorithm", havingValue = "ALWAYS")
class AlwaysAnomalyAnomalyDetector implements AnomalyDetector {

    @Override
    public Flux<Anomaly> detectAnomalies(Flux<TemperatureReading> temperatureReadings) {
        return temperatureReadings
                .map(Anomaly::new)
                .doOnNext(a -> log.debug("Anomaly detected: {}", a));
    }
}
