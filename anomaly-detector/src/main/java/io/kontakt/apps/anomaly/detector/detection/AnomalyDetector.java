package io.kontakt.apps.anomaly.detector.detection;

import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@FunctionalInterface
public interface AnomalyDetector {
    Flux<Anomaly> detectAnomalies(Flux<TemperatureReading> temperatureReadings);
}
