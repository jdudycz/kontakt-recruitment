package io.kontak.apps.event;

import java.time.Instant;

public record Anomaly(String thermometerId, String roomId, double temperature, Instant timestamp) {

    public Anomaly(TemperatureReading reading) {
        this(
                reading.thermometerId(),
                reading.roomId(),
                reading.temperature(),
                reading.timestamp()
        );
    }
}
