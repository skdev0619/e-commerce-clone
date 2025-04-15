package kr.hhplus.be.server.domain.order.discount

import java.math.BigDecimal

class PercentDiscountStrategy(private val rate: Int) : DiscountStrategy {
    init {
        require(rate in 1..100) { "1~100사이의 퍼센트만 입력 가능합니다" }
    }

    override fun applyDiscount(totalPrice: BigDecimal): BigDecimal {
        require(totalPrice >= BigDecimal.ZERO) { "총 금액은 0원 이상이어야 한다" }
        val discountAmount = totalPrice.multiply(BigDecimal(rate))
            .divide(BigDecimal(100))

        return totalPrice - discountAmount
    }
}

