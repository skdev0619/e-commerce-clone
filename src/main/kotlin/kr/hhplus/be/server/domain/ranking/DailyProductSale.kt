package kr.hhplus.be.server.domain.ranking

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity
import java.time.LocalDate

@Entity
class DailyProductSale(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false)
    val baseDate: LocalDate,

    @Column(nullable = false)
    val productId: Long,

    @Column(nullable = false)
    val salesCount: Long
) : BaseEntity() {
    constructor(baseDate: LocalDate, productId: Long, salesCount: Long) : this(0L, baseDate, productId, salesCount)
}
