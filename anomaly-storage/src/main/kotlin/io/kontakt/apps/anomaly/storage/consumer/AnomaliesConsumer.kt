package io.kontakt.apps.anomaly.storage.consumer

import io.kontak.apps.event.Anomaly
import io.kontakt.apps.anomaly.storage.data.AnomaliesRepository
import io.kontakt.apps.anomaly.storage.data.AnomalyDocument
import org.slf4j.LoggerFactory
import org.springframework.messaging.Message
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class AnomaliesConsumer(private val anomaliesRepository: AnomaliesRepository) {

    fun consume(anomalies: Flux<Message<Anomaly>>): Mono<Void> =
        anomalies
            .map(AnomalyDocument.Companion::fromMessage)
            .transform(anomaliesRepository::saveAll)
            .doOnNext { log.debug("Saved anomaly {}", it) }
            .then()

    companion object {
        private val log = LoggerFactory.getLogger(AnomaliesConsumer::class.java)
    }
}