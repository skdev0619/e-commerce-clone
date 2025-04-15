package kr.hhplus.be.server.domain.order

import kr.hhplus.be.server.domain.order.discount.DiscountStrategy

data class OrderCommand(
    val userId: Long,
    val orderItems: OrderItems,
    val couponIssueId: Long?,
    val discountStrategy: DiscountStrategy
)