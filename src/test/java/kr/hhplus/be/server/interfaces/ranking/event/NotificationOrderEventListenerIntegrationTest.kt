package kr.hhplus.be.server.interfaces.ranking.event

import kr.hhplus.be.server.domain.notification.NotificationInfo
import kr.hhplus.be.server.domain.notification.NotificationService
import kr.hhplus.be.server.domain.order.OrderEvent
import kr.hhplus.be.server.domain.order.OrderEventPublisher
import kr.hhplus.be.server.domain.order.OrderInfo
import kr.hhplus.be.server.domain.order.OrderItem
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.transaction.support.TransactionTemplate
import java.math.BigDecimal

@SpringBootTest
class NotificationOrderEventListenerIntegrationTest {

    @Autowired
    private lateinit var transactionTemplate: TransactionTemplate

    @Autowired
    private lateinit var eventPublisher: OrderEventPublisher

    @MockitoBean
    private lateinit var notificationService: NotificationService

    @DisplayName("주문 완료 이벤트가 수신되면, 데이터 플랫폼 전송 기능이 실행된다")
    @Test
    fun handleByOrderCompleteEvent() {
        val item = OrderItem(productId = 8L, quantity = 5, price = BigDecimal(5_000))
        //강제 commit 실행
        transactionTemplate.executeWithoutResult {
            eventPublisher.publish(createOrderCompleteEvent(item))
        }

        verify(notificationService).send(NotificationInfo("메세지타겟", "주문이 완료되었습니다."))
    }


    private fun createOrderCompleteEvent(item: OrderItem): OrderEvent.Completed {
        return OrderEvent.Completed(
            OrderInfo(userId = 98L, listOf(item), null)
        )
    }
}
