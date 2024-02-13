package io.kontak.apps.anomaly.detector.processing;

import io.kontak.apps.anomaly.detector.detection.AnomalyDetector;
import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.kstream.KStream;

import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
public class TemperatureAnomalyProcessor implements Function<KStream<String, TemperatureReading>, KStream<String, Anomaly>> {

    private final AnomalyDetector anomalyDetector;

    @Override
    public KStream<String, Anomaly> apply(KStream<String, TemperatureReading> events) {
        //TODO adapt to Recruitment Task requirements
        return events
                .mapValues((temperatureReading) -> anomalyDetector.apply(List.of(temperatureReading)))
                .filter((key, anomaly) -> anomaly.isPresent())
                .mapValues((key, anomaly) -> anomaly.get())
                .selectKey((key, anomaly) -> anomaly.thermometerId());
    }
}
