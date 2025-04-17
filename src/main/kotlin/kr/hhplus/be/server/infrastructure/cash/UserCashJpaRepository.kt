package kr.hhplus.be.server.infrastructure.cash

import kr.hhplus.be.server.domain.cash.UserCash
import org.springframework.data.jpa.repository.JpaRepository

interface UserCashJpaRepository : JpaRepository<UserCash, Long>{
    fun findByUserId(userId: Long): UserCash?
}
