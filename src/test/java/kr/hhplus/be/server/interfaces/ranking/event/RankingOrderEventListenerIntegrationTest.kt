package kr.hhplus.be.server.interfaces.ranking.event

import kr.hhplus.be.server.domain.order.OrderEvent
import kr.hhplus.be.server.domain.order.OrderEventPublisher
import kr.hhplus.be.server.domain.order.OrderInfo
import kr.hhplus.be.server.domain.order.OrderItem
import kr.hhplus.be.server.domain.ranking.DailyProductSaleRankingService
import kr.hhplus.be.server.domain.ranking.ProductSale
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.transaction.support.TransactionTemplate
import java.math.BigDecimal
import java.time.LocalDate

@SpringBootTest
class RankingOrderEventListenerIntegrationTest {

    @Autowired
    private lateinit var transactionTemplate: TransactionTemplate

    @Autowired
    private lateinit var eventPublisher: OrderEventPublisher

    @MockitoBean
    private lateinit var dailyProductSaleRankingService: DailyProductSaleRankingService

    @DisplayName("주문 완료 이벤트가 수신되면, 일별 상품 판매량을 누적 기능이 실행된다")
    @Test
    fun handleByOrderCompleteEvent() {
        val item = OrderItem(productId = 8L, quantity = 5, price = BigDecimal(5_000))
        //강제 commit 실행
        transactionTemplate.executeWithoutResult {
            eventPublisher.publish(createOrderCompleteEvent(item))
        }

        verify(dailyProductSaleRankingService).accumulate(
            LocalDate.now(),
            listOf(ProductSale(item.productId, item.quantity.toLong()))
        )
    }


    private fun createOrderCompleteEvent(item: OrderItem): OrderEvent.Completed {
        return OrderEvent.Completed(
            "orderId", OrderInfo(userId = 98L, listOf(item), null)
        )
    }
}
