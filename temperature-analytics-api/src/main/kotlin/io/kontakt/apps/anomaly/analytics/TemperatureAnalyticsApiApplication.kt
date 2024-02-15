package io.kontakt.apps.anomaly.analytics

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories


@SpringBootApplication
@EnableReactiveMongoRepositories
class TemperatureAnalyticsApiApplication

fun main(args: Array<String>) {
    runApplication<TemperatureAnalyticsApiApplication>(*args)
}

