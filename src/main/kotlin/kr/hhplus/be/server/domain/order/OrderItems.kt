package kr.hhplus.be.server.domain.order

import jakarta.persistence.*
import kr.hhplus.be.server.domain.order.discount.DiscountStrategy
import java.math.BigDecimal

@Embeddable
class OrderItems(

    @JoinColumn(
        name = "order_id",
        nullable = false,
        foreignKey = ForeignKey(name = "fk_orders_to_orderItem")
    )
    @OneToMany(
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE],
        orphanRemoval = true
    )
    val items: List<OrderItem> = ArrayList<OrderItem>()
) {
    fun totalPrice(): BigDecimal {
        return items.sumOf { it.amount() }
    }

    fun calculateDiscountPrice(discountStrategy: DiscountStrategy): BigDecimal {
        val totalPrice = totalPrice()
        val discountTotalPrice = discountStrategy.applyDiscount(totalPrice)
        return discountTotalPrice
    }
}
