package kr.hhplus.be.server.domain.cash

import java.util.concurrent.ConcurrentHashMap

class FakeUserCashRepository : UserCashRepository {

    private val userCashes = ConcurrentHashMap<Long, UserCash>()

    override fun save(cash: UserCash): UserCash {
        userCashes.put(cash.id, cash)
        return cash
    }

    override fun findByUserId(userId: Long): UserCash? {
        return userCashes.values.find { cash -> cash.userId == userId }
    }
}
