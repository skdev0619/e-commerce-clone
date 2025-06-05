package kr.hhplus.be.server.infrastructure.config.kafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.ByteArrayDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory

@Configuration
class ConsumerConfig {
    @Value("\${spring.kafka.consumer.bootstrap-servers}")
    private lateinit var BOOTSTRAP_ADDRESS: String

    @Value("\${spring.kafka.consumer.auto-offset-reset}")
    private lateinit var AUTO_OFFSET_RESET: String

    @Value("\${spring.kafka.consumer.enable-auto-commit}")
    private val AUTO_COMMIT = false

    @Bean
    fun consumerFactory(): ConsumerFactory<String, ByteArray> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = BOOTSTRAP_ADDRESS
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = AUTO_OFFSET_RESET
        props[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = AUTO_COMMIT
        return DefaultKafkaConsumerFactory(props, StringDeserializer(), ByteArrayDeserializer())
    }

    @Bean
    fun containerFactory(): ConcurrentKafkaListenerContainerFactory<String, ByteArray> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, ByteArray>()
        factory.consumerFactory = consumerFactory()
        return factory
    }
}