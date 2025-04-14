package kr.hhplus.be.server.interfaces.order

import kr.hhplus.be.server.application.order.OrderCompletedResult
import kr.hhplus.be.server.application.order.OrderItemResult
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderCreateResponse(
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
        fun from(result: OrderCompletedResult): OrderCreateResponse {
            return OrderCreateResponse(
                result.id,
                result.userId,
                result.status,
                result.issueCouponId,
                result.orderDateTime,
                result.orderItems,
                result.totalPrice,
                result.paymentId
            )
        }
    }
}
