package kr.hhplus.be.server.application.cash

import kr.hhplus.be.server.domain.cash.UserCashService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Transactional
@Service
class UserCashChargeService(
    private val userCashService: UserCashService
) {

    fun charge(userId: Long, amount: BigDecimal): UserCashChargeResult {
        val cash = userCashService.charge(userId, amount)
        return UserCashChargeResult(cash.id, cash.userId, amount, cash.balance)
    }
}
