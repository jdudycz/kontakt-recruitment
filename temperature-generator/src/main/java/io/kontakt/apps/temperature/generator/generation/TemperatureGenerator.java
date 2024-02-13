package io.kontakt.apps.temperature.generator.generation;

import io.kontak.apps.event.TemperatureReading;

import java.util.List;

@FunctionalInterface
public interface TemperatureGenerator {
    List<TemperatureReading> generate();
}
