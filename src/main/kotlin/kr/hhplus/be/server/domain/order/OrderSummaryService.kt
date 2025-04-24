package kr.hhplus.be.server.domain.order

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalTime

@Service
class OrderSummaryService(
    private val orderSummaryRepository: OrderSummaryRepository
) {
    @Transactional(readOnly = true)
    fun getProductSalesCountBy(
        baseDate: LocalDate
    ): List<OrderProductSalesInfo> {
        val startOfDay = baseDate.atStartOfDay()
        val endOfDay = baseDate.atTime(LocalTime.MAX)
        return orderSummaryRepository.getProductSalesCountBy(startOfDay, endOfDay)
    }
}

