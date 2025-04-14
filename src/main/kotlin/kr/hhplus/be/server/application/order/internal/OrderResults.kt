package kr.hhplus.be.server.application.order.internal

import kr.hhplus.be.server.domain.order.Order
import kr.hhplus.be.server.domain.order.OrderItems
import kr.hhplus.be.server.domain.order.OrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderCreateResult(
    val id: Long,
    val userId: Long,
    var status: OrderStatus,
    val issueCouponId: Long?,
    val orderDateTime: LocalDateTime,
    val orderItems: OrderItems,
    val totalPrice: BigDecimal
) {
    companion object {
        fun from(order: Order): OrderCreateResult {
            return OrderCreateResult(
                order.id,
                order.userId,
                order.status,
                order.issueCouponId,
                order.orderDateTime,
                order.orderItems,
                order.totalPrice
            )
        }
    }
}

class OrderPaymentResult(
    val paymentId: Long,
    val userId: Long,
    val orderId: Long,
    val amount: BigDecimal
)

