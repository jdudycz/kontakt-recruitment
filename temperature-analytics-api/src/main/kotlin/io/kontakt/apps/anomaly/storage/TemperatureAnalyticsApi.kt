package io.kontakt.apps.anomaly.storage

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TemperatureAnalyticsApi

fun main(args: Array<String>) {
    runApplication<TemperatureAnalyticsApi>(*args)
}

