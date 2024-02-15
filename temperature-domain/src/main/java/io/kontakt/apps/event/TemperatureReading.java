package io.kontakt.apps.event;

import java.time.Instant;

public record TemperatureReading(String thermometerId, String roomId, double temperature, Instant timestamp) {
}
