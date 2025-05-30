package kr.hhplus.be.server.config

import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.AdminClientConfig
import org.springframework.context.annotation.Configuration
import org.testcontainers.kafka.KafkaContainer
import org.testcontainers.utility.DockerImageName
import java.util.*

@Configuration
class KafkaTestcontainers {

    companion object {
        private val kafkaContainer: KafkaContainer = KafkaContainer(
            DockerImageName.parse("apache/kafka-native:3.8.0"),
        ).apply {
            portBindings = listOf("9092:9092")
            start()
        }

        val bootstrapServers: String
            get() = kafkaContainer.bootstrapServers

        fun getAdminClient(): AdminClient {
            val props = Properties()
            props[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
            return AdminClient.create(props)
        }
    }
}
