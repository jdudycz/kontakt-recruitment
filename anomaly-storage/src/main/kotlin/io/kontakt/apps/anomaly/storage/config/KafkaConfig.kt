package io.kontakt.apps.anomaly.storage.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.kontakt.apps.anomaly.storage.consumer.AnomaliesConsumer
import io.kontakt.apps.event.Anomaly
import io.kontakt.apps.serialization.ObjectMapperFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import reactor.core.publisher.Flux
import java.util.function.Consumer


@Configuration
class KafkaConfig(private val anomaliesConsumer: AnomaliesConsumer) {


    @Bean
    fun anomalyConsumer(): Consumer<Flux<Message<Anomaly>>> =
        Consumer { anomaliesConsumer.consume(it).subscribe() }

    @Bean
    fun objectMapper(): ObjectMapper = ObjectMapperFactory.createJsonMapper()
}
