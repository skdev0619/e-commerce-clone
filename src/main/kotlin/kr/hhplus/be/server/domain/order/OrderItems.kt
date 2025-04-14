package kr.hhplus.be.server.domain.order

import java.math.BigDecimal

class OrderItems(
    val items: List<OrderItem>
) {
    fun totalPrice(): BigDecimal {
        return items.sumOf { it.amount() }
    }

    val productQuantityPairs: List<Pair<Long, Int>> = items.map {
        Pair(it.productId, it.quantity)
    }
}