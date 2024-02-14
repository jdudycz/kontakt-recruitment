package io.kontakt.apps.thermometer.simulator.generation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Instant;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class TemperatureGeneratorImplTest {

    private final static int BASE_SPREAD = 2;
    private final static int ANOMALY_MIN = 5;

    private final Clock clock = Mockito.mock(Clock.class);

    @BeforeEach
    void beforeEach() {
        Mockito.reset(clock);
    }

    @Test
    @DisplayName("[generate] should generate reading with thermometerId and roomId set from properties")
    public void generateReadProperties() {
        //given
        var properties = new ThermometerProperties("thermometer-1", "rooom-1", 20, 0.1);
        var cut = new TemperatureGeneratorImpl(properties, clock);

        // when
        var reading = cut.generate();

        // then
        assertThat(reading.thermometerId())
                .isEqualTo(properties.thermometerId());
        assertThat(reading.roomId())
                .isEqualTo(properties.roomId());
    }

    @Test
    @DisplayName("[generate] should generate reading with current timestamp")
    public void generateTimestamp() {
        //given
        var properties = new ThermometerProperties("thermometer-1", "rooom-1", 20, 0.1);
        var cut = new TemperatureGeneratorImpl(properties, clock);

        var currentTime = Instant.now();
        Mockito.when(clock.instant()).thenReturn(currentTime);

        // when
        var reading = cut.generate();

        // then
        assertThat(reading.timestamp()).isEqualTo(currentTime);
    }


    @Test
    @DisplayName("[generate] should generate all values within spread bounds on 0 anomaly rate")
    public void generateNoAnomalies() {
        //given
        var properties = getSamplePropertiesWithAnomaly(0.0);
        var cut = new TemperatureGeneratorImpl(properties, clock);

        // when
        var readings = IntStream.range(0, 1000).mapToDouble(it -> cut.generate().temperature());

        // then
        assertThat(readings.allMatch(it -> Math.abs(it - properties.tempBase()) < BASE_SPREAD)).isTrue();
    }

    @Test
    @DisplayName("[generate] should generate some values with abs distance to temp base higher than spread on non 0 anomaly rate")
    public void generateAnomalies() {
        //given
        var properties = getSamplePropertiesWithAnomaly(0.2);
        var cut = new TemperatureGeneratorImpl(properties, clock);

        // when
        var readings = IntStream.range(0, 1000).mapToDouble(it -> cut.generate().temperature());

        // then
        assertThat(readings.anyMatch(it -> it - properties.tempBase() >= ANOMALY_MIN)).isTrue();
    }


    private ThermometerProperties getSamplePropertiesWithAnomaly(double anomalyRate) {
        return new ThermometerProperties("thermometer-1", "rooom-1", 20, anomalyRate);
    }

}
