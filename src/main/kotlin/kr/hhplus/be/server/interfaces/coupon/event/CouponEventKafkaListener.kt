package kr.hhplus.be.server.interfaces.coupon.event

import com.fasterxml.jackson.databind.ObjectMapper
import kr.hhplus.be.server.domain.coupon.CouponEvent
import kr.hhplus.be.server.domain.coupon.CouponService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class CouponEventKafkaListener(
    private val objectMapper: ObjectMapper,
    private val couponService: CouponService,
) {

    @KafkaListener(
        topics = ["coupon.tryIssued"],
        groupId = "coupon.issue"
    )
    fun consume(message: ConsumerRecord<String, ByteArray>, ack: Acknowledgment) {
        val event = objectMapper.readValue(message.value(), CouponEvent.tryIssued::class.java)
        couponService.issue(event.userId, event.couponId)
        ack.acknowledge()
    }
}
