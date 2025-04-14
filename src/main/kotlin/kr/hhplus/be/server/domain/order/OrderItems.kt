package kr.hhplus.be.server.domain.order

import kr.hhplus.be.server.domain.order.discount.DiscountStrategy
import java.math.BigDecimal

class OrderItems(
    val items: List<OrderItem>
) {
    fun originTotalPrice(): BigDecimal {
        return items.sumOf { it.amount() }
    }

    fun calculatePrice(discountStrategy: DiscountStrategy): BigDecimal {
        val totalPrice = originTotalPrice()
        val discountTotalPrice = discountStrategy.applyDiscount(totalPrice)
        return discountTotalPrice
    }
}