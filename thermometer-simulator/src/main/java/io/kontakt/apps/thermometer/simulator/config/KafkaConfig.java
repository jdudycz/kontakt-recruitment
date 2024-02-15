package io.kontakt.apps.thermometer.simulator.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kontakt.apps.event.TemperatureReading;
import io.kontakt.apps.serialization.ObjectMapperFactory;
import io.kontakt.apps.thermometer.simulator.publishing.TemperatureStreamPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Configuration
public class KafkaConfig {

    @Bean
    public Supplier<Flux<Message<TemperatureReading>>> messageProducer(TemperatureStreamPublisher publisher) {
        return publisher::getMessageProducer;
    }

    @Bean
    public static ObjectMapper objectMapper() {
        return ObjectMapperFactory.createJsonMapper();
    }
}
