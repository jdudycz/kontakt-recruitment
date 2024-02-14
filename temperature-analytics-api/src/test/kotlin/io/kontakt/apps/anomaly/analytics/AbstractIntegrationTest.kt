package io.kontakt.apps.anomaly.analytics

import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers


@Testcontainers
@AutoConfigureWebTestClient
@SpringBootTest(classes = [TemperatureAnalyticsApi::class, MongoConfig::class])
abstract class AbstractIntegrationTest {

}