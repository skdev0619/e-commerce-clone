package kr.hhplus.be.server.domain.cash

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity
import java.math.BigDecimal

@Entity
@Table(
    indexes = [
        Index(name = "idx_user_id", columnList = "userId")
    ]
)
class UserCashHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false, unique = true)
    val userId: Long,

    @Enumerated(EnumType.STRING)
    val type: TransactionType,

    @Column(nullable = false)
    val balance: BigDecimal,

    ) : BaseEntity() {
    constructor(userId: Long, type: TransactionType, balance: BigDecimal) : this(0L, userId, type, balance)
}
