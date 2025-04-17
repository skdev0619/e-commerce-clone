package kr.hhplus.be.server.domain.order

import org.springframework.data.domain.Pageable
import java.time.LocalDateTime

interface OrderSummaryRepository {
    fun findTopSellingProducts(startDate: LocalDateTime, endDate: LocalDateTime, pageable: Pageable) : List<OrderProductSalesInfo>
}
