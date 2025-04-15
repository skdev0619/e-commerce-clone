package kr.hhplus.be.server.domain.order.discount

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.math.BigDecimal

class PercentDiscountStrategyTest{

    @DisplayName("할인률은 1~100사이의 값이 아니면 예외 발생한다")
    @ValueSource(ints = [-1, 0, 101])
    @ParameterizedTest
    fun validateRate(rate : Int) {
        assertThrows<IllegalArgumentException> {
            PercentDiscountStrategy(rate)
        }
    }

    @DisplayName("총 금액이 음수면 예외 발생한다")
    @Test
    fun validateTotalPrice() {
        val strategy = PercentDiscountStrategy(10)
        val totalPrice = BigDecimal(-1)

        assertThrows<IllegalArgumentException> {
            strategy.applyDiscount(totalPrice)
        }
    }

    @DisplayName("총 금액에서 정률만큼 할인하여 계산한다")
    @Test
    fun applyDiscount() {
        val strategy = PercentDiscountStrategy(10)
        val totalPrice = BigDecimal(10_000)

        val discountedPrice = strategy.applyDiscount(totalPrice)

        assertThat(discountedPrice).isEqualTo(BigDecimal(9_000))
    }
}

