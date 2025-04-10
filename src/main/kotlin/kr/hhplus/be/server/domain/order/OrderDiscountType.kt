package kr.hhplus.be.server.domain.order

import java.math.BigDecimal

//TODO 할인 정책 복잡해지면 나중에 인터페이스 분리

enum class OrderDiscountType {
    COUPON_FIXED {
        override fun calculate(totalPrice: BigDecimal, discountValue: Int): BigDecimal {
            val discountAmount = BigDecimal(discountValue)
            return (totalPrice - discountAmount).coerceAtLeast(BigDecimal.ZERO)
        }
    },
    COUPON_RATE {
        override fun calculate(totalPrice: BigDecimal, discountValue: Int): BigDecimal {
            val discountRate = BigDecimal(discountValue)
            val discountAmount = totalPrice.multiply(discountRate).divide(BigDecimal(100))
            return (totalPrice - discountAmount).coerceAtLeast(BigDecimal.ZERO)
        }
    };

    abstract fun calculate(totalPrice: BigDecimal, discountValue: Int): BigDecimal
}
