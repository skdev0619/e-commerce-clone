package kr.hhplus.be.server.infrastructure.order

import kr.hhplus.be.server.domain.order.OrderEvent
import kr.hhplus.be.server.domain.order.OrderEventPublisher
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class OrderKafkaEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) : OrderEventPublisher {

    override fun publish(event: OrderEvent.Completed) {
        kafkaTemplate.send("order.completed", event.id, event)
    }
}
