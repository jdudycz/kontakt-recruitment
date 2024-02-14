package io.kontakt.apps.anomaly.storage.data

import io.kontakt.apps.anomaly.storage.data.model.AnomalyDocument
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface AnomaliesRepository : ReactiveMongoRepository<AnomalyDocument, String>, CustomAnomaliesRepository {
    fun findByThermometerId(thermometerId: String): Flux<AnomalyDocument>
    fun findByRoomId(roomId: String): Flux<AnomalyDocument>

//    @Aggregation(
//        pipeline = [
//            "{\$group: { _id: '\$thermometerId', anomalyCount: { \$count: {} } }} " +
////                    "{\$match: {anomalyCount: { \$gt: '\$anomaliesCount'}}}" +
//                    "{\$group: {_id: null }}"]
//    )
//    fun findTest(anomaliesCount: Int): Flux<Map<String, Any>>
}
