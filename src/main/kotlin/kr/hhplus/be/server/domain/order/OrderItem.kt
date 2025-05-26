package kr.hhplus.be.server.domain.order

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import kr.hhplus.be.server.domain.common.BaseEntity
import java.math.BigDecimal

@Entity
class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false)
    val productId: Long,

    @Column(nullable = false)
    val quantity: Int,

    @Column(nullable = false)
    val price: BigDecimal
) : BaseEntity() {
    constructor(productId: Long, quantity: Int, price: BigDecimal) : this(0L, productId, quantity, price)

    fun amount(): BigDecimal {
        return price.multiply(BigDecimal(quantity))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OrderItem

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}

