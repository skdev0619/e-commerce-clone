package kr.hhplus.be.server.infrastructure.cash

import kr.hhplus.be.server.domain.cash.UserCashHistory
import org.springframework.data.jpa.repository.JpaRepository

interface UserCashJpaHistoryRepository : JpaRepository<UserCashHistory, Long> {
    fun findByUserId(userId: Long): List<UserCashHistory>
}