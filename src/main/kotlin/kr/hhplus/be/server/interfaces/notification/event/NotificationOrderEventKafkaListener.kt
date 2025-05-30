package kr.hhplus.be.server.interfaces.notification.event

import kr.hhplus.be.server.domain.notification.NotificationInfo
import kr.hhplus.be.server.domain.notification.NotificationSender
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class NotificationOrderEventKafkaListener(
    private val notificationSender: NotificationSender
) {

    @KafkaListener(
        topics = ["order.completed"],
        groupId = "notification-order-completed"
    )
    fun consume(message: ConsumerRecord<String, ByteArray>, ack: Acknowledgment) {
        val message = "주문이 완료되었습니다."
        notificationSender.send(NotificationInfo("메세지타겟", message))
        ack.acknowledge()
    }
}
