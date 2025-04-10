package kr.hhplus.be.server.application.cash

import java.math.BigDecimal

data class UserCashViewResult(
    val id: Long,
    val userId: Long,
    var balance: BigDecimal
)
