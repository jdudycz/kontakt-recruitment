package io.kontakt.apps.thermometer.simulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@ConfigurationPropertiesScan
public class ThermometerSimulatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThermometerSimulatorApplication.class, args);
    }
}
