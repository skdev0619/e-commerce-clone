package kr.hhplus.be.server.domain.order.discount

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.math.BigDecimal

class FixedDiscountStrategyTest {

    @DisplayName("할인 하고자하는 금액이 0원이면 예외 발생한다")
    @Test
    fun validateFixedAmount() {
        assertThrows<IllegalArgumentException> {
            FixedDiscountStrategy(0)
        }
    }

    @DisplayName("총 금액이 0원 이하면 예외 발생한다")
    @Test
    fun validateTotalPrice() {
        val strategy = FixedDiscountStrategy(1000)
        val totalPrice = BigDecimal(-1)

        assertThrows<IllegalArgumentException> {
            strategy.applyDiscount(totalPrice)
        }
    }

    @DisplayName("총 금액에서 정액을 차감하여 반환한다")
    @Test
    fun applyDiscount() {
        val strategy = FixedDiscountStrategy(1_000)
        val totalPrice = BigDecimal(5_000)

        val discountedPrice = strategy.applyDiscount(totalPrice)

        assertThat(discountedPrice).isEqualTo(BigDecimal(4000))
    }

    @DisplayName("할인 금액이 총 금액과 같거나 크면 0원을 반환한다")
    @ValueSource(ints = [5_000, 5_001])
    @ParameterizedTest
    fun discountAmountOver(amount: Int) {
        val strategy = FixedDiscountStrategy(amount)
        val totalPrice = BigDecimal(5000)

        val discountedPrice = strategy.applyDiscount(totalPrice)

        assertThat(discountedPrice).isZero()
    }
}
