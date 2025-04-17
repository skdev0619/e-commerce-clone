package kr.hhplus.be.server.infrastructure.cash

import kr.hhplus.be.server.domain.cash.UserCashHistory
import kr.hhplus.be.server.domain.cash.UserCashHistoryRepository
import org.springframework.stereotype.Repository

@Repository
class UserCashHistoryRepositoryImpl(
    private val jpaRepository: UserCashJpaHistoryRepository
) : UserCashHistoryRepository {

    override fun save(history: UserCashHistory): UserCashHistory {
        return jpaRepository.save(history)
    }

    override fun findByUserId(userId: Long): List<UserCashHistory> {
        return jpaRepository.findByUserId(userId)
    }
}
