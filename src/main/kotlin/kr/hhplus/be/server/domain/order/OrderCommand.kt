package kr.hhplus.be.server.domain.order

import kr.hhplus.be.server.domain.order.discount.DiscountStrategy

data class OrderCommand(
    val userId: Long,
    val orderItems: OrderItems,
    val couponIssueId: Long?,
    val discountStrategy: DiscountStrategy
)

data class OrderInfo(
    val userId: Long,
    val orderItems: List<OrderItem>,
    val couponIssueId: Long?
) {
    companion object {
        fun from(command: OrderCommand): OrderInfo {
            return OrderInfo(
                command.userId,
                command.orderItems.items,
                command.couponIssueId
            )
        }
    }
}
