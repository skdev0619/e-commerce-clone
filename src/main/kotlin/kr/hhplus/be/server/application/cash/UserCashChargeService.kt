package kr.hhplus.be.server.application.cash

import kr.hhplus.be.server.domain.cash.UserCashService
import org.springframework.orm.ObjectOptimisticLockingFailureException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Transactional
@Service
class UserCashChargeService(
    private val userCashService: UserCashService
) {

    fun charge(userId: Long, amount: BigDecimal): UserCashChargeResult {
        try {
            val cash = userCashService.charge(userId, amount)
            return UserCashChargeResult(cash.id, cash.userId, amount, cash.balance)
        } catch (e: ObjectOptimisticLockingFailureException) {
            throw RuntimeException("동시에 충전 요청이 처리되어 실패했습니다. 잠시 후 다시 시도해주세요.", e)
        }
    }
}
