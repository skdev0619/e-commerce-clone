package kr.hhplus.be.server.domain.order.discount

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class NoDiscountStrategyTest {

    @DisplayName("할인이 없으므로 총 금액 그대로 반환한다")
    @Test
    fun noDiscount() {
        val strategy = NoDiscountStrategy()
        val totalPrice = BigDecimal(10_000)

        val discountedPrice = strategy.applyDiscount(totalPrice)

        assertThat(discountedPrice).isEqualTo(totalPrice)
    }

    @DisplayName("총 금액은 0원 미만이면 예외 발생한다")
    @Test
    fun validateTotalPrice() {
        val strategy = NoDiscountStrategy()
        val totalPrice = BigDecimal(-1)

        assertThrows<IllegalArgumentException> {
            strategy.applyDiscount(totalPrice)
        }
    }
}