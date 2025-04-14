package kr.hhplus.be.server.application.order

import kr.hhplus.be.server.domain.payment.Payment
import kr.hhplus.be.server.domain.order.Order
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderItemResult(
    val productId: Long,
    val quantity: Int,
    val price: BigDecimal
)

data class OrderCompletedResult(
    val id: Long,
    val userId: Long,
    var status: String,
    val issueCouponId: Long?,
    val orderDateTime: LocalDateTime,
    val orderItems: List<OrderItemResult>,
    val totalPrice: BigDecimal,
    val paymentId: Long
) {
    companion object {
        fun from(order: Order, payment: Payment): OrderCompletedResult {
            val items = order.orderItems.items.map { OrderItemResult(it.productId, it.quantity, it.price) }

            return OrderCompletedResult(
                order.id,
                order.userId,
                order.status.name,
                order.issueCouponId,
                order.orderDateTime,
                items,
                order.totalPrice,
                payment.id
            )
        }
    }

}
