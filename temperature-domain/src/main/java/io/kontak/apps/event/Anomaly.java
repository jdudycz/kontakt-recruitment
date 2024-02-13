package io.kontak.apps.event;

import java.time.Instant;

public record Anomaly(double temperature, String roomId, String thermometerId, Instant timestamp) {

    public Anomaly(TemperatureReading reading) {
        this(
                reading.temperature(),
                reading.roomId(),
                reading.thermometerId(),
                reading.timestamp()
        );
    }
}
