package io.kontakt.apps.anomaly.detector.utils;

import io.kontakt.apps.event.Anomaly;
import io.kontakt.apps.event.TemperatureReading;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.util.Random;

@UtilityClass
public class Fixtures {

    private static final Random random = new Random();


    public static Anomaly aAnomaly(double temp) {
        return new Anomaly(
                "test-thermometer",
                "test-room",
                temp,
                Instant.now());
    }

    public static TemperatureReading aReading(double temp) {
        return new TemperatureReading(
                "test-thermometer",
                "test-room",
                temp,
                Instant.now());
    }

    public static TemperatureReading aReading() {
        return new TemperatureReading(
                "test-thermometer",
                "test-room",
                random.nextDouble(10, 30),
                Instant.now());
    }
}
