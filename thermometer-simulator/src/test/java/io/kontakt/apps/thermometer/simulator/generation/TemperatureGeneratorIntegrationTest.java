package io.kontakt.apps.thermometer.simulator.generation;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TemperatureGeneratorIntegrationTest {

    @Autowired
    private ThermometerProperties properties;

    @Autowired
    private TemperatureGeneratorImpl cut;

    @Test
    @DisplayName("[generate] should generate reading based on properties from application.properties")
    public void generate() {
        // when
        var reading = cut.generate();

        // then
        assertThat(reading.thermometerId())
                .isNotBlank()
                .isEqualTo(properties.thermometerId());
        assertThat(reading.roomId())
                .isNotBlank()
                .isEqualTo(properties.roomId());
    }
}
