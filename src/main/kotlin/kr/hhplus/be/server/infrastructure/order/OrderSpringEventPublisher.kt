package kr.hhplus.be.server.infrastructure.order

import kr.hhplus.be.server.domain.order.OrderEvent
import kr.hhplus.be.server.domain.order.OrderEventPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class OrderSpringEventPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher
) : OrderEventPublisher {

    override fun publish(event: OrderEvent.Completed) {
        applicationEventPublisher.publishEvent(event)
    }
}
