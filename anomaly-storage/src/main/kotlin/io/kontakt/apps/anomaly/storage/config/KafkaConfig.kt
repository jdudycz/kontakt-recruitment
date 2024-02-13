package io.kontakt.apps.anomaly.storage.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.kontak.apps.event.Anomaly
import io.kontakt.apps.anomaly.storage.consumer.AnomaliesConsumer
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import reactor.core.publisher.Flux
import java.util.*
import java.util.function.Consumer


@Configuration
class KafkaConfig(private val anomaliesConsumer: AnomaliesConsumer) {


    @Bean
    fun anomalyConsumer(): Consumer<Flux<Message<Anomaly>>> =
        Consumer { anomaliesConsumer.consume(it).subscribe() }

    @Bean
    fun objectMapper(): JsonMapper =
        JsonMapper.builder()
            .addModule(JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
            .defaultDateFormat(StdDateFormat().withTimeZone(TimeZone.getTimeZone("UTC")).withColonInTimeZone(false))
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build()

    companion object {
        private val log = LoggerFactory.getLogger(KafkaConfig::class.java)
    }
}
