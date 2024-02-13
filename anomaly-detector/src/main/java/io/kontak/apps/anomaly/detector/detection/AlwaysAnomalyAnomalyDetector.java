package io.kontak.apps.anomaly.detector.detection;

import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
class AlwaysAnomalyAnomalyDetector implements AnomalyDetector {

    @Override
    public Flux<Anomaly> apply(Flux<TemperatureReading> temperatureReadings) {
        return temperatureReadings.map(tempReading -> new Anomaly(
                tempReading.temperature(),
                tempReading.roomId(),
                tempReading.thermometerId(),
                tempReading.timestamp()
        ));
    }
}
