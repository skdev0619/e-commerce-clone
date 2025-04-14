package kr.hhplus.be.server.application.cash

import java.math.BigDecimal

data class UserCashChargeResult(
    val id: Long,
    val userId: Long,
    val amount: BigDecimal,
    var finalBalance: BigDecimal
)
