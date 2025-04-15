package kr.hhplus.be.server.domain.order.discount

import java.math.BigDecimal

class NoDiscountStrategy : DiscountStrategy {
    override fun applyDiscount(totalPrice: BigDecimal): BigDecimal {
        require(totalPrice >= BigDecimal.ZERO) { "총 금액은 0원 이상이어야 한다" }
        return totalPrice
    }
}
