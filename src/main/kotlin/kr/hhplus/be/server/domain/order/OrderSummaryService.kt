package kr.hhplus.be.server.domain.order

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OrderSummaryService(
    private val orderSummaryRepository: OrderSummaryRepository
) {
    fun findTopSellingProducts(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        limit: Int
    ): List<OrderProductSalesInfo> {
        val pageable = Pageable.ofSize(limit)
        return orderSummaryRepository.findTopSellingProducts(startDate, endDate, pageable)
    }
}
