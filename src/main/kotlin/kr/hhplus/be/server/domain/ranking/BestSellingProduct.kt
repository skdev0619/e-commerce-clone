package kr.hhplus.be.server.domain.ranking

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity
import java.math.BigDecimal
import java.time.LocalDate

@Entity
class BestSellingProduct(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false, length = 8)
    val startDate: LocalDate,

    @Column(nullable = false, length = 8)
    val endDate: LocalDate,

    @Column(nullable = false)
    val productId: Long,

    @Column(nullable = false)
    val salesCount: Long,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val price: BigDecimal
) : BaseEntity() {

    constructor(
        startDate: LocalDate,
        endDate: LocalDate,
        productId: Long,
        salesCount: Long,
        name: String,
        price: BigDecimal
    ) : this(0L, startDate, endDate, productId, salesCount, name, price)
}