package kr.hhplus.be.server.domain.cash

interface UserCashRepository {
    fun save(cash: UserCash): UserCash
    fun findByUserId(userId: Long): UserCash?
}