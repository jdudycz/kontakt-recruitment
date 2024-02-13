package io.kontak.apps.temperature.generator;

import io.kontak.apps.event.TemperatureReading;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import static io.kontak.apps.event.Constants.MSG_ID_HEADER;

@Component
public class TemperatureStreamPublisher {

    private final Sinks.Many<Message<TemperatureReading>> messageProducer = Sinks.many().multicast().onBackpressureBuffer();

    public Flux<Message<TemperatureReading>> getMessageProducer() {
        return messageProducer.asFlux();
    }

    public void publish(TemperatureReading temperatureReading) {
        messageProducer.tryEmitNext(
                MessageBuilder.withPayload(temperatureReading)
                        .setHeader(MSG_ID_HEADER, temperatureReading.thermometerId())
                        .build()
        );
    }
}
