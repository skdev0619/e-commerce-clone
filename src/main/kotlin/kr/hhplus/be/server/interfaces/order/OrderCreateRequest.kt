package kr.hhplus.be.server.interfaces.order

import kr.hhplus.be.server.application.order.OrderCriteria
import kr.hhplus.be.server.application.order.OrderItemCommand

data class OrderItemRequest(
    val productId: Long,
    val quantity: Int,
    val price: Long
)

data class OrderCreateRequest(
    val userId: Long,
    val orderItems: List<OrderItemRequest>,
    val issueCouponId: Long?
) {
    companion object {
        fun from(request: OrderCreateRequest): OrderCriteria {
            val orderItems = request.orderItems.map { OrderItemCommand(it.productId, it.quantity, it.price) }
            return OrderCriteria(request.userId, orderItems, request.issueCouponId)
        }
    }
}