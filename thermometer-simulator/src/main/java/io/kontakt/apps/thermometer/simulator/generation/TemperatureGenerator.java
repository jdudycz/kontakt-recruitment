package io.kontakt.apps.thermometer.simulator.generation;

import io.kontakt.apps.event.TemperatureReading;

@FunctionalInterface
public interface TemperatureGenerator {
    TemperatureReading generate();
}
