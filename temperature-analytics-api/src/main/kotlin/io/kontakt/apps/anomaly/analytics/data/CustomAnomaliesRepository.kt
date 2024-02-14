package io.kontakt.apps.anomaly.analytics.data

import io.kontakt.apps.anomaly.analytics.data.model.ThermometerIdsAggregation
import reactor.core.publisher.Mono

interface CustomAnomaliesRepository {

    fun findThermometerIdsAboveThreshold(anomaliesCount: Int): Mono<ThermometerIdsAggregation>
}
