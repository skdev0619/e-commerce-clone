package kr.hhplus.be.server.interfaces.ranking.event

import kr.hhplus.be.server.domain.order.OrderEvent
import kr.hhplus.be.server.domain.ranking.DailyProductSaleRankingService
import kr.hhplus.be.server.domain.ranking.ProductSale
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import java.time.LocalDate

@Component
class RankingOrderEventListener(
    private val dailyProductSaleRankingService: DailyProductSaleRankingService
) {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handle(event: OrderEvent.Completed) {
        val productSales = event.orderInfo.orderItems.map {
            ProductSale(it.productId, it.quantity.toLong())
        }
        dailyProductSaleRankingService.accumulate(LocalDate.now(), productSales)
    }
}
