package kr.hhplus.be.server.domain.order.discount

import java.math.BigDecimal

class FixedDiscountStrategy(private val fixAmount: Int) : DiscountStrategy {
    init {
        require(fixAmount > 0) { "할인할 금액은 0원을 넘어야 합니다" }
    }

    override fun applyDiscount(totalPrice: BigDecimal): BigDecimal {
        require(totalPrice >= BigDecimal.ZERO) { "총 금액은 0원 이상이어야 합니다" }
        val fixAmount = BigDecimal(fixAmount)
        if (totalPrice >= fixAmount) {
            return totalPrice - fixAmount
        }
        return BigDecimal.ZERO
    }
}
