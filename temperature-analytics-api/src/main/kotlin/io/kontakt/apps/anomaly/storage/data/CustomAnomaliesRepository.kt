package io.kontakt.apps.anomaly.storage.data

import io.kontakt.apps.anomaly.storage.data.model.ThermometerIdsAggregation
import reactor.core.publisher.Mono

interface CustomAnomaliesRepository {

    fun findThermometerIdsAboveThreshold(anomaliesCount: Int): Mono<ThermometerIdsAggregation>
}
