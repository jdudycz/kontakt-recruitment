package io.kontakt.apps.thermometer.simulator.generation;

import io.kontak.apps.event.TemperatureReading;

import java.util.List;

@FunctionalInterface
public interface TemperatureGenerator {
    TemperatureReading generate();
}
