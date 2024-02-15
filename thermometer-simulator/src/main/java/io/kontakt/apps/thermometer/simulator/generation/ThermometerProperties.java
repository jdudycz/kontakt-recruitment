package io.kontakt.apps.thermometer.simulator.generation;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Validated
@ConfigurationProperties("thermometer-simulator")
public record ThermometerProperties(
        String thermometerId,
        String roomId,
        double baseTemp,
        @DecimalMin("0.0")
        @DecimalMax("0.2")
        double anomalyRate) {
    public ThermometerProperties {
        if (isBlank(thermometerId)) thermometerId = UUID.randomUUID().toString();
        if (isBlank(roomId)) roomId = UUID.randomUUID().toString();
    }
}
