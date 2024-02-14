package io.kontakt.apps.thermometer.simulator.generation;

import io.kontak.apps.event.TemperatureReading;

@FunctionalInterface
public interface TemperatureGenerator {
    TemperatureReading generate();
}
