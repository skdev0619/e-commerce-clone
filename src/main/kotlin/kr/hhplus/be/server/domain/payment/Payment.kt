package kr.hhplus.be.server.domain.payment

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity
import java.math.BigDecimal

@Entity
class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false)
    val orderId: Long,

    @Column(nullable = false)
    val amount: BigDecimal,

    ) : BaseEntity() {

    constructor(orderId: Long, amount: BigDecimal)
            : this(
        id = 0L,
        orderId = orderId,
        amount = amount
    )
}