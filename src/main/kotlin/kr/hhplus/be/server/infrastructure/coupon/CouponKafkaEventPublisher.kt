package kr.hhplus.be.server.infrastructure.coupon

import kr.hhplus.be.server.domain.coupon.CouponEvent
import kr.hhplus.be.server.domain.coupon.CouponEventPublisher
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class CouponKafkaEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) : CouponEventPublisher {

    override fun publish(event: CouponEvent.tryIssued) {
        kafkaTemplate.send("coupon.tryIssued", event.couponId.toString(), event)
    }
}
