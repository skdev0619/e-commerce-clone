package kr.hhplus.be.server.domain.order

import kr.hhplus.be.server.domain.common.AuditInfo
import kr.hhplus.be.server.domain.order.discount.DiscountStrategy
import java.math.BigDecimal
import java.time.LocalDateTime

class Order(
    val id: Long,
    val userId: Long,
    var status: OrderStatus,
    val issueCouponId: Long?,
    val orderDateTime: LocalDateTime = LocalDateTime.now(),
    val orderItems: OrderItems,
    var totalPrice: BigDecimal,
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
        fun create(userId: Long, issueCouponId: Long?, orderItems: OrderItems): Order {
            val totalPrice = orderItems.totalPrice()
            return Order(
                userId = userId,
                status = OrderStatus.CREATED,
                issueCouponId = issueCouponId,
                orderDateTime = LocalDateTime.now(),
                orderItems = orderItems,
                totalPrice = totalPrice
            )
        }
    }

    fun applyDiscount(discountStrategy: DiscountStrategy){
        val discountedPrice = orderItems.calculateDiscountPrice(discountStrategy)
        totalPrice = discountedPrice
    }

    fun completePayment() {
        if (status != OrderStatus.CREATED) {
            throw IllegalStateException("주문의 상태가 이상합니다")
        }
        status = OrderStatus.PAID
    }
}
