package kr.hhplus.be.server.infrastructure.cash

import kr.hhplus.be.server.domain.cash.UserCash
import kr.hhplus.be.server.domain.cash.UserCashRepository
import org.springframework.stereotype.Repository

@Repository
class UserCashJpaRepository : UserCashRepository {
    override fun save(cash: UserCash): UserCash {
        TODO("Not yet implemented")
    }

    override fun findByUserId(userId: Long): UserCash? {
        TODO("Not yet implemented")
    }
}