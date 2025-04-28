package kr.hhplus.be.server.domain.order

import java.time.LocalDateTime

interface OrderSummaryRepository {
    fun getProductSalesCountBy(startTime: LocalDateTime, endTime: LocalDateTime): List<OrderProductSalesInfo>
}
