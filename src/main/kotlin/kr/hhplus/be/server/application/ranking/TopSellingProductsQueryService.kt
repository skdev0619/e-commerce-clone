package kr.hhplus.be.server.application.ranking

import kr.hhplus.be.server.domain.order.OrderSummaryService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class TopSellingProductsQueryService(
    private val orderSummaryService: OrderSummaryService
) {
    fun findTopSellingProducts(query: TopSellingProductsQuery): List<TopSellingProductsQueryResult> {
        val salesInfos = orderSummaryService.findTopSellingProducts(query.startDate, query.endDate, query.limit)

        return salesInfos.map { TopSellingProductsQueryResult(it.productId, it.sales) }
    }
}
