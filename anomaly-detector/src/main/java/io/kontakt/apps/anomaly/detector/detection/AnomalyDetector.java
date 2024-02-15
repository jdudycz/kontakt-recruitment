package io.kontakt.apps.anomaly.detector.detection;

import io.kontakt.apps.event.Anomaly;
import io.kontakt.apps.event.TemperatureReading;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface AnomalyDetector {
    Flux<Anomaly> detectAnomalies(Flux<TemperatureReading> temperatureReadings);
}
