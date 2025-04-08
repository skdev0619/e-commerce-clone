package kr.hhplus.be.server.order.presentation.dto

data class OrderItemDto(
    val productId: Long,
    val quantity: Int,
    val price: Int
)

data class OrderCreateRequest(
    val userId: Long,
    val orderItems: List<OrderItemDto>,
    val couponId: Long?
)

data class OrderCreateResponse(
    val id: Long,
    val orderItem: List<OrderItemDto>,
    val totalAmount: Int,
    val discountAmount: Int,
    val finalAmount: Int
)