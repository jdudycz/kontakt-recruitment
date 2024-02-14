package io.kontakt.apps.anomaly.analytics.api

import io.kontakt.apps.anomaly.analytics.AbstractIntegrationTest
import io.kontakt.apps.anomaly.analytics.data.model.AnomalyDocument
import io.kontakt.apps.anomaly.analytics.data.model.ThermometerIdsAggregation
import org.bson.types.ObjectId
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.annotation.PostConstruct


class AnomaliesControllerIntegrationTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var testClient: WebTestClient

    @Autowired
    private lateinit var mongoTemplate: ReactiveMongoTemplate

    private val anomalies = listOf(
        aDocument("thermometer-1", "room-1"),
        aDocument("thermometer-1", "room-1"),
        aDocument("thermometer-2", "room-2"),
        aDocument("thermometer-2", "room-2"),
        aDocument("thermometer-2", "room-2"),
        aDocument("thermometer-3", "room-2")
    )

    @PostConstruct
    fun init() {
        mongoTemplate.remove(Query(), "anomalies")
            .thenMany(Flux.concat(anomalies.map(mongoTemplate::save)))
            .blockLast()
    }

    @Test
    @DisplayName("[getAnomaliesForThermometer] should return anomalies with given thermometerId")
    fun getAnomaliesForThermometerListAnomalies() {
        testClient.get().uri("/anomalies/thermometer/thermometer-1").exchange()
            .expectStatus().isOk
            .expectBodyList(AnomalyDocument::class.java)
            .contains(anomalies[0])
            .contains(anomalies[1])
            .hasSize(2)
    }

    @Test
    @DisplayName("[getAnomaliesForThermometer] should return empty list on none matching")
    fun getAnomaliesForThermometerEmpty() {
        testClient.get().uri("/anomalies/thermometer/non-existing").exchange()
            .expectStatus().isOk
            .expectBodyList(AnomalyDocument::class.java)
            .hasSize(0)
    }

    @Test
    @DisplayName("[getAnomaliesForRoom] should return anomalies with given roomId")
    fun getAnomaliesForRoomListAnomalies() {
        testClient.get().uri("/anomalies/room/room-2").exchange()
            .expectStatus().isOk
            .expectBodyList(AnomalyDocument::class.java)
            .contains(anomalies[2])
            .contains(anomalies[3])
            .contains(anomalies[4])
            .contains(anomalies[5])
            .hasSize(4)
    }

    @Test
    @DisplayName("[getAnomaliesForRoom] should return empty list on none matching")
    fun getAnomaliesForRoomEmpty() {
        testClient.get().uri("/anomalies/room/non-existing").exchange()
            .expectStatus().isOk
            .expectBodyList(AnomalyDocument::class.java)
            .hasSize(0)
    }

    @Test
    @DisplayName("[getThermometersAboveThreshold] should return thermometer ids with anomaly count over threshold")
    fun getThermometersAboveThresholdListThermometers() {
        testClient.get().uri("/anomalies/thermometers?anomalyThreshold=2").exchange()
            .expectStatus().isOk
            .expectBody(ThermometerIdsAggregation::class.java)
            .isEqualTo(ThermometerIdsAggregation(setOf("thermometer-1", "thermometer-2")))
    }


    @Test
    @DisplayName("[getThermometersAboveThreshold] should return empty ids list on none matching")
    fun getThermometersAboveThresholdEmpty() {
        testClient.get().uri("/anomalies/thermometers?anomalyThreshold=5").exchange()
            .expectStatus().isOk
            .expectBody(ThermometerIdsAggregation::class.java)
            .isEqualTo(ThermometerIdsAggregation(emptySet()))
    }

    private fun aDocument(thermometerId: String, roomId: String) =
        AnomalyDocument(
            ObjectId.get().toHexString(),
            thermometerId,
            roomId,
            123.1,
            Instant.now().truncatedTo(ChronoUnit.MILLIS)
        )
}