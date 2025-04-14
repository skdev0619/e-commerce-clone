package kr.hhplus.be.server.domain.order

import kr.hhplus.be.server.domain.common.AuditInfo
import java.math.BigDecimal
import java.time.LocalDateTime

class Order(
    val id: Long,
    val userId: Long,
    var status: OrderStatus,
    val issueCouponId: Long?,
    val orderDateTime: LocalDateTime = LocalDateTime.now(),
    val orderItems: OrderItems,
    val totalPrice: BigDecimal,
    val auditInfo: AuditInfo = AuditInfo()
) {
    constructor(userId: Long, status: OrderStatus, issueCouponId: Long?, orderItems: OrderItems, totalPrice: BigDecimal)
            : this(0, userId, status, issueCouponId, LocalDateTime.now(), orderItems, totalPrice, AuditInfo())

    val productQuantityPairs = orderItems.productQuantityPairs
}
