package io.kontakt.apps.anomaly.detector.detection;

import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Slf4j
@Component
@ConditionalOnProperty(name = "anomaly-detector.algorithm", havingValue = "BATCH")
class BatchAnomalyDetector implements AnomalyDetector {

    private final static int BATCH_SIZE = 10;

    @Override
    public Flux<Anomaly> apply(Flux<TemperatureReading> temperatureReadings) {
        return temperatureReadings.window(BATCH_SIZE)
                .flatMap(Flux::collectList)
                .flatMap(this::detectAnomalies);
    }

    private Flux<Anomaly> detectAnomalies(List<TemperatureReading> readings) {
        return Flux.defer(() -> {
            var readingsValues = readings.stream().map(TemperatureReading::temperature).toList();
            log.debug("Checking batch for anomalies {}", readingsValues);
            return Flux.fromIterable(readings)
                    .flatMap(reading -> checkForAnomaly(reading, readings))
                    .doOnNext(a -> log.debug("Anomaly detected: {}", a));
        });
    }

    private Mono<Anomaly> checkForAnomaly(TemperatureReading selectedReading, List<TemperatureReading> allReadings) {
        var average = allReadings.stream()
                .filter(it -> !it.equals(selectedReading))
                .mapToDouble(TemperatureReading::temperature)
                .average()
                .orElse(Integer.MAX_VALUE);
        return selectedReading.temperature() - average > 5
                ? Mono.just(new Anomaly(selectedReading))
                : Mono.empty();
    }
}


