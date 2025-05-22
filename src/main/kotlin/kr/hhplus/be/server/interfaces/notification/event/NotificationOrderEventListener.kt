package kr.hhplus.be.server.interfaces.notification.event

import kr.hhplus.be.server.domain.notification.NotificationInfo
import kr.hhplus.be.server.domain.notification.NotificationService
import kr.hhplus.be.server.domain.order.OrderEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class NotificationOrderEventListener(
    private val notificationService: NotificationService,
) {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handle(event: OrderEvent.Completed) {
        val message = "주문이 완료되었습니다."
        notificationService.send(NotificationInfo("메세지타겟", message))
    }
}
