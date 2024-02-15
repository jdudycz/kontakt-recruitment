package io.kontakt.apps.thermometer.simulator.publishing;

import io.kontakt.apps.event.TemperatureReading;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import static io.kontakt.apps.event.Constants.MSG_ID_HEADER;

@Slf4j
@Component
public class TemperatureStreamPublisher {

    private final Sinks.Many<Message<TemperatureReading>> messageProducer = Sinks.many().multicast().onBackpressureBuffer();

    public Flux<Message<TemperatureReading>> getMessageProducer() {
        return messageProducer.asFlux()
                .doOnNext(msg -> log.debug("Temperature reading {} published", msg.getPayload()));
    }

    public void publish(TemperatureReading temperatureReading) {
        messageProducer.tryEmitNext(
                MessageBuilder.withPayload(temperatureReading)
                        .setHeader(MSG_ID_HEADER, temperatureReading.thermometerId())
                        .build()
        );
    }
}
