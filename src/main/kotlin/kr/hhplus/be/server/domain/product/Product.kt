package kr.hhplus.be.server.domain.product

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity
import java.math.BigDecimal

@Entity
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val price: BigDecimal,

    @Column(nullable = false)
    var stock: Int,
) : BaseEntity() {
    constructor(name: String, price: Int, stock: Int) : this(0L, name, BigDecimal(price), stock)

    fun validateStock(requestStock: Int) {
        if (this.stock < requestStock) {
            throw IllegalArgumentException("상품의 재고가 부족합니다")
        }
    }

    fun decreaseStock(quantity: Int) {
        validateStock(quantity)
        stock -= quantity
    }
}
