package kr.hhplus.be.server.infrastructure.order

import kr.hhplus.be.server.domain.order.OrderProductSalesInfo
import kr.hhplus.be.server.domain.order.OrderSummaryRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class OrderSummaryRepositoryImpl(
    private val jpaRepository: OrderSummaryJpaRepository
) : OrderSummaryRepository {

    override fun getProductSalesCountBy(startTime: LocalDateTime, endTime: LocalDateTime): List<OrderProductSalesInfo> {
        return jpaRepository.getProductSalesCountBy(startTime, endTime)
    }
}
