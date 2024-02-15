package io.kontakt.apps.anomaly.detector.detection;

import io.kontakt.apps.event.Anomaly;
import io.kontakt.apps.anomaly.detector.utils.Fixtures;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.stream.Stream;

public class BatchAnomalyDetectorTest {

    private final BatchAnomalyDetector cut = new BatchAnomalyDetector();

    @Test
    @DisplayName("should not detect anomalies in valid data")
    public void detectAnomaliesNoAnomalies() {
        // given
        var readings = Flux.just(20.0, 24.99, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0)
                .map(Fixtures::aReading);

        // when, then
        StepVerifier.create(readings.transform(cut::detectAnomalies)).verifyComplete();
    }

    @Test
    @DisplayName("should detect anomaly if temperature at least 5 degrees higher than rest average")
    public void detectAnomaliesOneAnomaly() {
        // given
        var readings = Stream.of(20.0, 25.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0)
                .map(Fixtures::aReading).toList();

        // when, then
        StepVerifier.create(Flux.fromIterable(readings).transform(cut::detectAnomalies))
                .expectNext(new Anomaly(readings.get(1)))
                .verifyComplete();
    }

    @Test
    @DisplayName("should detect multiple anomalies")
    public void detectAnomaliesMultipleAnomalies() {
        // given
        var readings = Stream.of(20.1, 28.4, 23.6, 21.5, 19.5, 17.5, 19.2, 29.0, 22.9, 17.6)
                .map(Fixtures::aReading).toList();

        // when, then
        StepVerifier.create(Flux.fromIterable(readings).transform(cut::detectAnomalies))
                .expectNext(new Anomaly(readings.get(1)))
                .expectNext(new Anomaly(readings.get(7)))
                .verifyComplete();
    }

    @Test
    @DisplayName("should not detect anomaly on temperature much lower than average")
    public void detectAnomaliesMuchLowerTemp() {
        // given
        var readings = Flux.just(20.1, 13.4, 23.6, 21.5, 19.5, 17.5, 19.2, 20.0, 22.9, 17.6)
                .map(Fixtures::aReading);

        // when, then
        StepVerifier.create(readings.transform(cut::detectAnomalies)).verifyComplete();
    }


    @Test
    @DisplayName("should window anomalies by 10 elements")
    public void detectAnomaliesWindow() {
        // given
        var readings = Flux.just(20.1, 18.4, 23.6, 21.5, 19.5, 17.5, 19.2, 20.0, 22.9, 17.6,
                        30.1, 28.4, 33.6, 31.5, 29.5, 27.5, 29.2, 30.0, 32.9, 27.6)
                .map(Fixtures::aReading);

        // when, then
        StepVerifier.create(readings.transform(cut::detectAnomalies)).verifyComplete();
    }


    @Test
    @DisplayName("should detect anomaly in second window")
    public void detectAnomaliesAnomalyNextWindow() {
        // given
        var readings = Stream.of(20.1, 18.4, 23.6, 21.5, 19.5, 17.5, 19.2, 20.0, 22.9, 17.6,
                        30.1, 28.4, 33.6, 31.5, 39.5, 27.5, 29.2, 30.0, 32.9, 27.6)
                .map(Fixtures::aReading).toList();

        // when, then
        StepVerifier.create(Flux.fromIterable(readings).transform(cut::detectAnomalies))
                .expectNext(new Anomaly(readings.get(14)))
                .verifyComplete();
    }

    @Test
    @DisplayName("should detect anomaly in last window in flux if it's not complete")
    public void detectAnomaliesAnomalyLastWindow() {
        // given
        var readings = Stream.of(20.1, 18.4, 23.6, 21.5, 19.5, 17.5, 19.2, 20.0, 22.9, 17.6,
                        20.1, 28.4, 23.6)
                .map(Fixtures::aReading).toList();

        // when, then
        StepVerifier.create(Flux.fromIterable(readings).transform(cut::detectAnomalies))
                .expectNext(new Anomaly(readings.get(11)))
                .verifyComplete();
    }
}
