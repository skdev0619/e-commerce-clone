package kr.hhplus.be.server.application.order

import kr.hhplus.be.server.domain.order.OrderCommand
import kr.hhplus.be.server.domain.order.OrderItem
import kr.hhplus.be.server.domain.order.OrderItems
import kr.hhplus.be.server.domain.order.discount.DiscountStrategy
import kr.hhplus.be.server.domain.product.ProductQuantity
import java.math.BigDecimal

data class OrderItemCommand(
    val productId: Long,
    val quantity: Int,
    val price: Long
)

data class OrderCriteria(
    val userId: Long,
    val orderItems: List<OrderItemCommand>,
    val issueCouponId: Long?
) {

    fun toProductQuantities(): List<ProductQuantity> {
        return orderItems.map {
            ProductQuantity(it.productId, it.quantity)
        }.toList()
    }

    fun toOrderCommand(discountStrategy: DiscountStrategy): OrderCommand {
        return OrderCommand(
            userId, toOrderItems(), issueCouponId, discountStrategy
        )
    }

    private fun toOrderItems(): OrderItems {
        return OrderItems(
            orderItems.map { OrderItem(it.productId, it.quantity, BigDecimal(it.price)) }
        )
    }
}
