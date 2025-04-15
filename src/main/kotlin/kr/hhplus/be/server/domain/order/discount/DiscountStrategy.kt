package kr.hhplus.be.server.domain.order.discount

import java.math.BigDecimal

interface DiscountStrategy {
    fun applyDiscount(totalPrice: BigDecimal): BigDecimal
}
