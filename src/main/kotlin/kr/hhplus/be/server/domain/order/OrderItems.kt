package kr.hhplus.be.server.domain.order

import kr.hhplus.be.server.domain.order.discount.DiscountStrategy
import java.math.BigDecimal

class OrderItems(
    val items: List<OrderItem>
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