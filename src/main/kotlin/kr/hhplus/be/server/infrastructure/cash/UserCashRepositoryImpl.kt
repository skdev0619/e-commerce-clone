package kr.hhplus.be.server.infrastructure.cash

import kr.hhplus.be.server.domain.cash.UserCash
import kr.hhplus.be.server.domain.cash.UserCashRepository
import org.springframework.stereotype.Repository

@Repository
class UserCashRepositoryImpl(
    private val jpaRepository: UserCashJpaRepository
) : UserCashRepository {

    override fun save(cash: UserCash): UserCash {
        return jpaRepository.save(cash)
    }

    override fun findByUserId(userId: Long): UserCash? {
        return jpaRepository.findByUserId(userId)
    }
}
