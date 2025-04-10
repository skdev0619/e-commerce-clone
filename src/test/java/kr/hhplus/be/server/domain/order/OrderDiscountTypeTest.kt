package kr.hhplus.be.server.domain.order

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class OrderDiscountTypeTest {

    @Nested
    @DisplayName("정액 할인")
    inner class FixedAmountDiscount {

        @DisplayName("정액 할인을 적용하면 해당 금액만큼 차감된다")
        @Test
        fun discountByFixedAmount() {
            val totalPrice = BigDecimal(10_000)
            val discountValue = 3000

            val discountedPrice = OrderDiscountType.COUPON_FIXED.calculate(totalPrice, discountValue)

            assertThat(discountedPrice).isEqualTo(BigDecimal(7_000))
        }

        @DisplayName("주문 금액보다 할인 금액이 큰 경우, 총 주문 금액은 0원이 된다")
        @Test
        fun discountByOverPrice() {
            val totalPrice = BigDecimal(2_000)
            val discountValue = 5000

            val discountedPrice = OrderDiscountType.COUPON_FIXED.calculate(totalPrice, discountValue)

            assertThat(discountedPrice).isEqualTo(BigDecimal.ZERO)
        }
    }

    @Nested
    @DisplayName("정률 할인")
    inner class RateDiscount {

        @DisplayName("정률 할인을 적용하면 비율만큼 차감된다")
        @Test
        fun discountByRate() {
            val totalPrice = BigDecimal(10_000)
            val discountRate = 10

            val discountedPrice = OrderDiscountType.COUPON_RATE.calculate(totalPrice, discountRate)

            assertThat(discountedPrice).isEqualTo(BigDecimal(9_000))
        }

        @DisplayName("주문 금액보다 할인 금액이 큰 경우, 총 주문 금액은 0원이 된다")
        @Test
        fun discountByOverPrice() {
            val totalPrice = BigDecimal(500)
            val discountValue = 200

            val discountedPrice = OrderDiscountType.COUPON_RATE.calculate(totalPrice, discountValue)

            assertThat(discountedPrice).isEqualTo(BigDecimal.ZERO)
        }
    }
}
