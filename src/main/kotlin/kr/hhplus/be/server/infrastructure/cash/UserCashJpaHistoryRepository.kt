package kr.hhplus.be.server.infrastructure.cash

import kr.hhplus.be.server.domain.cash.UserCashHistory
import kr.hhplus.be.server.domain.cash.UserCashHistoryRepository
import org.springframework.stereotype.Repository

@Repository
class UserCashJpaHistoryRepository : UserCashHistoryRepository {

    override fun save(history: UserCashHistory): UserCashHistory {
        TODO("Not yet implemented")
    }

    override fun findByUserId(userId: Long): List<UserCashHistory> {
        TODO("Not yet implemented")
    }
}