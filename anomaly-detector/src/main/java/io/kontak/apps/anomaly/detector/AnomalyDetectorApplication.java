package io.kontak.apps.anomaly.detector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@ConfigurationPropertiesScan
public class AnomalyDetectorApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnomalyDetectorApplication.class, args);
    }
}
