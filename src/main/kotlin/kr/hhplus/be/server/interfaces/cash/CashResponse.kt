package kr.hhplus.be.server.interfaces.cash

import kr.hhplus.be.server.application.cash.UserCashChargeResult
import kr.hhplus.be.server.application.cash.UserCashViewResult
import java.math.BigDecimal

data class CashViewResponse(
    val id: Long,
    val userId: Long,
    var balance: BigDecimal
) {
    companion object {
        fun from(cash: UserCashViewResult): CashViewResponse {
            return CashViewResponse(cash.id, cash.userId, cash.balance)
        }
    }
}

data class CashChargeResponse(
    val id: Long,
    val userId: Long,
    val amount: BigDecimal,
    var finalBalance: BigDecimal
) {
    companion object {
        fun from(cash: UserCashChargeResult): CashChargeResponse {
            return CashChargeResponse(cash.id, cash.userId, cash.amount, cash.finalBalance)
        }
    }
}
