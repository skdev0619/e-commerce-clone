package kr.hhplus.be.server.domain.cash

import kr.hhplus.be.server.domain.common.AuditInfo
import java.math.BigDecimal

class UserCashHistory(
    val id: Long,
    val userId: Long,
    val type: TransactionType,
    val balance: BigDecimal,
    val auditInfo: AuditInfo = AuditInfo()
) {
    constructor(userId: Long, type: TransactionType, balance: BigDecimal) : this(0L, userId, type, balance)
}