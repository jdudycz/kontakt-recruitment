package io.kontakt.apps.thermometer.simulator.publishing;

import io.kontakt.apps.thermometer.simulator.generation.TemperatureGenerator;
import io.kontakt.apps.thermometer.simulator.generation.ThermometerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Slf4j
@Component
@RequiredArgsConstructor
public class ThermometerSimulatorJob {

    private final ThermometerProperties properties;
    private final TemperatureGenerator generator;
    private final TemperatureStreamPublisher publisher;

    @Scheduled(fixedRateString = "${thermometer-simulator.read-rate-millis}")
    public void publishReading() {
        publisher.publish(generator.generate());
    }

    @PostConstruct
    public void initialize() {
        log.info("Thermometer simulator started. Configuration: {}", properties);
    }
}
