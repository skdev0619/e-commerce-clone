package kr.hhplus.be.server.domain.cash

import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class UserCashService(
    private val userCashRepository: UserCashRepository,
    private val userCashHistoryRepository: UserCashHistoryRepository
) {

    fun findByUserId(userId: Long): UserCash {
        return userCashRepository.findByUserId(userId)
            ?: throw NoSuchElementException("해당 사용자의 잔액이 존재하지 않습니다")
    }

    fun charge(userId: Long, amount: BigDecimal): UserCash {
        val userCash = findByUserId(userId)

        userCash.charge(amount)
        userCashHistoryRepository.save(UserCashHistory(userId, TransactionType.CHARGE, amount))

        return userCash
    }

    fun use(userId: Long, amount: BigDecimal): UserCash {
        val userCash = findByUserId(userId)

        userCash.use(amount)
        userCashHistoryRepository.save(UserCashHistory(userId, TransactionType.USE, amount))

        return userCash
    }
}
