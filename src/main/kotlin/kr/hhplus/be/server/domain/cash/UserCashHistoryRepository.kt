package kr.hhplus.be.server.domain.cash

interface UserCashHistoryRepository {
    fun save(history: UserCashHistory): UserCashHistory

    fun findByUserId(userId: Long): List<UserCashHistory>
}
