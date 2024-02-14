package io.kontakt.apps.anomaly.storage.data

import io.kontak.apps.event.Anomaly
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.messaging.Message
import java.time.Instant

@Document("anomalies")
data class AnomalyDocument(
    @Id var id: String?,
    val thermometerId: String,
    val roomId: String,
    val temperature: Double,
    val timestamp: Instant
) {
    companion object {
        fun fromMessage(message: Message<Anomaly>) =
            message.payload.run { AnomalyDocument(null, thermometerId, roomId, temperature, timestamp) }
    }
}
