package kr.hhplus.be.server.domain.order

import kr.hhplus.be.server.domain.common.AuditInfo
import java.math.BigDecimal
import java.time.LocalDateTime

class Order(
    val id: Long,
    val userId: Long,
    var status: OrderStatus,
    val issueCouponId: Long?,
    val orderDateTime: LocalDateTime = LocalDateTime.now(),
    val orderItems: OrderItems,
    val totalPrice: BigDecimal,
    val auditInfo: AuditInfo = AuditInfo()
) {
    constructor(
        userId: Long,
        status: OrderStatus,
        issueCouponId: Long?,
        orderDateTime: LocalDateTime,
        orderItems: OrderItems,
        totalPrice: BigDecimal
    )
            : this(0, userId, status, issueCouponId, orderDateTime, orderItems, totalPrice, AuditInfo())

    companion object {
        fun create(userId: Long, issueCouponId: Long?, orderItems: OrderItems, totalPrice: BigDecimal): Order {
            return Order(
                userId = userId,
                status = OrderStatus.CREATED,
                issueCouponId = issueCouponId,
                orderDateTime = LocalDateTime.now(),
                orderItems = orderItems,
                totalPrice = totalPrice,
            )
        }
    }

    fun completePayment() {
        status = OrderStatus.PAID
    }
}
