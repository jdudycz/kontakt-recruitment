package io.kontakt.apps.anomaly.storage.data.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("anomalies")
data class AnomalyDocument(
    @Id val id: String,
    val thermometerId: String,
    val roomId: String,
    val temperature: Double,
    val timestamp: Instant
)
