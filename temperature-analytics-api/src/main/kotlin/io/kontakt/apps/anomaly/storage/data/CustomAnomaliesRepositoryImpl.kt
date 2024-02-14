package io.kontakt.apps.anomaly.storage.data

import io.kontakt.apps.anomaly.storage.data.model.ThermometerIdsAggregation
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.aggregate
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class CustomAnomaliesRepositoryImpl(private val mongoTemplate: ReactiveMongoTemplate) : CustomAnomaliesRepository {

    override fun findThermometerIdsAboveThreshold(anomaliesCount: Int): Mono<ThermometerIdsAggregation> {
        val aggregation = newAggregation(
            group("thermometerId").count().`as`("anomalyCount"),
            match(Criteria("anomalyCount").gt(anomaliesCount)),
            group().push("_id").`as`("thermometerIds"),
            project().andExclude("_id")
        )
        return Mono.from(mongoTemplate.aggregate<ThermometerIdsAggregation>(aggregation, "anomalies"))
            .defaultIfEmpty(ThermometerIdsAggregation(emptyList()))
    }
}