package kr.hhplus.be.server.application.payment

import kr.hhplus.be.server.domain.common.AuditInfo
import java.math.BigDecimal

class Payment(
    val id: Long,
    val userId: Long,
    val orderId: Long,
    val amount: BigDecimal,
    val auditInfo: AuditInfo = AuditInfo()
) {
    constructor(userId: Long, orderId: Long, amount: BigDecimal) : this(0L, userId, orderId, amount)
}