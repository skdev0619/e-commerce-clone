package kr.hhplus.be.server.infrastructure.order

import kr.hhplus.be.server.domain.order.Order
import kr.hhplus.be.server.domain.order.OrderProductSalesInfo
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface OrderSummaryJpaRepository : JpaRepository<Order, Long> {

    @Query(
        "SELECT new kr.hhplus.be.server.domain.order.OrderProductSalesInfo(oi.productId, sum(oi.quantity)) " +
                "FROM Order o " +
                "JOIN  o.orderItems.items oi " +
                "WHERE o.paidDate BETWEEN :startDate AND :endDate " +
                "AND o.status = 'PAID' " +
                "GROUP BY oi.productId " +
                "ORDER BY SUM(oi.quantity) DESC "
    )
    fun findTopSellingProductsBy(
        @Param("startDate") startDate: LocalDateTime,
        @Param("endDate") endDate: LocalDateTime,
        pageable: Pageable
    ): List<OrderProductSalesInfo>
}
