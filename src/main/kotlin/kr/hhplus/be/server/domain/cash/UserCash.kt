package kr.hhplus.be.server.domain.cash

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity
import java.math.BigDecimal

@Entity
class UserCash(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false, unique = true)
    val userId: Long,

    @Column(nullable = false)
    var balance: BigDecimal,

    ) : BaseEntity() {
    constructor(userId: Long, balance: Int) : this(0L, userId, BigDecimal(balance))

    fun charge(amount: BigDecimal) {
        require(amount > BigDecimal.ZERO) { "충전 금액은 0원보다 커야 합니다" }
        balance += amount
    }

    fun use(amount: BigDecimal) {
        require(amount > BigDecimal.ZERO) { "사용할 금액은 0원보다 커야 합니다" }
        check(balance >= amount) { "잔액이 부족합니다" }
        balance -= amount
    }
}
