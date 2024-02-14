package io.kontakt.apps.anomaly.analytics.data

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AnomaliesRepository : ReactiveMongoRepository<AnomalyDocument, String>
