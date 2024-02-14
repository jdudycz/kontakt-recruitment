package io.kontakt.apps.thermometer.simulator.generation;

import io.kontak.apps.event.TemperatureReading;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Random;

@Component
@RequiredArgsConstructor
class TemperatureGeneratorImpl implements TemperatureGenerator {

    private final static int BASE_SPREAD = 2;
    private final static int ANOMALY_MIN = 5;
    private final static Random random = new Random();

    private final ThermometerProperties thermometerProperties;

    @Override
    public TemperatureReading generate() {
        var temp = generateTemp(thermometerProperties.tempBase());
        return new TemperatureReading(
                thermometerProperties.thermometerId(),
                thermometerProperties.roomId(),
                temp,
                Instant.now()
        );
    }

    private double generateTemp(double tempBase) {
        var anomalyBonus = anomalyOccurred() ? random.nextDouble(ANOMALY_MIN, ANOMALY_MIN * 2) : 0;
        return random.nextDouble(tempBase - BASE_SPREAD, tempBase + BASE_SPREAD) + anomalyBonus;
    }

    private boolean anomalyOccurred() {
        return random.nextDouble(0.0, 1.0) < thermometerProperties.anomalyRate();
    }
}
