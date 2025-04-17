package kr.hhplus.be.server.application.cash

import kr.hhplus.be.server.domain.cash.UserCashService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class UserCashQueryService(
    private val userCashService: UserCashService
) {
    fun findByUserId(userId: Long): UserCashViewResult {
        val cash = userCashService.findByUserId(userId)
        return UserCashViewResult(cash.id, cash.userId, cash.balance)
    }
}
