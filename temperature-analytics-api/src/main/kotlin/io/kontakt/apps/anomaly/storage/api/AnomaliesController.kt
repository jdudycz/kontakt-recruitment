package io.kontakt.apps.anomaly.storage.api

import io.kontakt.apps.anomaly.storage.data.AnomaliesRepository
import io.kontakt.apps.anomaly.storage.data.model.AnomalyDocument
import io.kontakt.apps.anomaly.storage.data.model.ThermometerIdsAggregation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/anomalies")
class AnomaliesController(private val anomaliesRepository: AnomaliesRepository) {

    @GetMapping("/thermometer/{thermometerId}")
    fun getAnomaliesForThermometer(@PathVariable thermometerId: String): Flux<AnomalyDocument> {
        return anomaliesRepository.findByThermometerId(thermometerId)
    }

    @GetMapping("/room/{roomId}")
    fun getAnomaliesForRoom(@PathVariable roomId: String): Flux<AnomalyDocument> {
        return anomaliesRepository.findByRoomId(roomId)
    }

    @GetMapping("/thermometers", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getThermometersAboveThreshold(@RequestParam anomalyThreshold: Int): Mono<ThermometerIdsAggregation> {
        return anomaliesRepository.findThermometerIdsAboveThreshold(anomalyThreshold)
    }
}