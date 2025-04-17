package kr.hhplus.be.server.infrastructure.order

import kr.hhplus.be.server.domain.order.OrderProductSalesInfo
import kr.hhplus.be.server.domain.order.OrderSummaryRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class OrderSummaryRepositoryImpl(
    private val jpaRepository: OrderSummaryJpaRepository
) : OrderSummaryRepository {

    override fun findTopSellingProducts(startDate: LocalDateTime, endDate: LocalDateTime, pageable: Pageable): List<OrderProductSalesInfo> {

        return jpaRepository.findTopSellingProductsBy(startDate, endDate, PageRequest.of(0, 5) )
    }
}
