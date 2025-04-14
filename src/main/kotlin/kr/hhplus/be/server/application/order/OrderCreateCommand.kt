package kr.hhplus.be.server.application.order

import kr.hhplus.be.server.domain.order.OrderItem
import kr.hhplus.be.server.domain.order.OrderItems
import java.math.BigDecimal

data class OrderItemCommand(
    val productId: Long,
    val quantity: Int,
    val price: Long
)

data class OrderCreateCommand(
    val userId: Long,
    val orderItems: List<OrderItemCommand>,
    val issueCouponId: Long?
) {

    fun toProductIds(): List<Long> {
        return orderItems.map { it.productId }
    }

    fun toOrderItems(): OrderItems {
        return OrderItems(
            orderItems.map { OrderItem(it.productId, it.quantity, BigDecimal(it.price)) }
        )
    }
}