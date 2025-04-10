package kr.hhplus.be.server.domain.cash

import kr.hhplus.be.server.domain.common.AuditInfo
import java.math.BigDecimal

class UserCash(
    val id: Long,
    val userId: Long,
    var balance: BigDecimal,
    val auditInfo: AuditInfo = AuditInfo()
) {
    constructor(userId: Long, balance: Int) : this(0L, userId, BigDecimal(balance))

    fun charge(amount: BigDecimal) {
        require(amount > BigDecimal.ZERO) { "충전 금액은 0원보다 커야 합니다" }
        balance += amount
        auditInfo.update()
    }

    fun use(amount: BigDecimal) {
        require(amount > BigDecimal.ZERO) { "사용할 금액은 0원보다 커야 합니다" }
        require(balance >= amount) { "잔액이 부족합니다" }
        balance -= amount
        auditInfo.update()
    }
}
