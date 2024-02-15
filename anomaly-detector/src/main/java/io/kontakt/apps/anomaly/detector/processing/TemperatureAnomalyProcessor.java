package io.kontakt.apps.anomaly.detector.processing;

import io.kontakt.apps.anomaly.detector.detection.AnomalyDetector;
import io.kontakt.apps.event.Anomaly;
import io.kontakt.apps.event.TemperatureReading;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import reactor.core.publisher.Flux;

import java.util.function.Function;

import static io.kontakt.apps.event.Constants.MSG_ID_HEADER;

@RequiredArgsConstructor
public class TemperatureAnomalyProcessor implements Function<Flux<Message<TemperatureReading>>, Flux<Message<Anomaly>>> {

    private final AnomalyDetector anomalyDetector;

    @Override
    public Flux<Message<Anomaly>> apply(Flux<Message<TemperatureReading>> events) {
        return events
                .map(Message::getPayload)
                .transform(anomalyDetector::detectAnomalies)
                .map(this::createAnomalyMessage);
    }

    private Message<Anomaly> createAnomalyMessage(Anomaly anomaly) {
        return MessageBuilder.withPayload(anomaly)
                .setHeader(MSG_ID_HEADER, anomaly.thermometerId())
                .build();
    }
}
