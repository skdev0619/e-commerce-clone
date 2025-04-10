package kr.hhplus.be.server.domain.cash

import java.util.concurrent.ConcurrentHashMap

class FakeUserCashHistoryRepository : UserCashHistoryRepository {

    private val histories = ConcurrentHashMap<Long, UserCashHistory>()

    override fun save(history: UserCashHistory): UserCashHistory {
        histories.put(history.id, history)
        return history
    }

    override fun findByUserId(userId: Long): List<UserCashHistory> {
        return histories.values.filter { it.userId == userId }
    }
}