package io.kontakt.apps.anomaly.analytics

import org.springframework.context.annotation.Configuration
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.utility.DockerImageName

@Configuration
class MongoConfig {
    companion object {
        @Container
        val mongoContainer: MongoDBContainer = MongoDBContainer(DockerImageName.parse("mongo:7.0-jammy"))
            .withExposedPorts(27017)

        init {
            mongoContainer.start()
        }

        @DynamicPropertySource
        fun datasourceConfig(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.port") { mongoContainer.firstMappedPort }
        }
    }
}