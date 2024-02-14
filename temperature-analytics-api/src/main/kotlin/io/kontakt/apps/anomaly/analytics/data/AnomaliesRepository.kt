package io.kontakt.apps.anomaly.analytics.data

import io.kontakt.apps.anomaly.analytics.data.model.AnomalyDocument
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface AnomaliesRepository : ReactiveMongoRepository<AnomalyDocument, String>, CustomAnomaliesRepository {
    fun findByThermometerId(thermometerId: String): Flux<AnomalyDocument>
    fun findByRoomId(roomId: String): Flux<AnomalyDocument>
}
