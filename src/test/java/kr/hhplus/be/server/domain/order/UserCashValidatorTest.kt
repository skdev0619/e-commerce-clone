package kr.hhplus.be.server.domain.order

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.math.BigDecimal

class UserCashValidatorTest{
    private val validator = UserCashValidator()


    @DisplayName("잔액이 총 주문금액 이상이면 예외 발생하지 않는다")
    @ParameterizedTest
    @ValueSource(ints = [999, 1000])
    fun enoughCash(balance : Int) {
        val cash = OrderCashSnapShot(1L, BigDecimal(1_000))
        val totalPrice = BigDecimal(balance)

        assertDoesNotThrow {
            validator.validate(cash, totalPrice)
        }
    }

    @DisplayName("잔액이 총 주문금액보다 작으면 예외 발생한다")
    @Test
    fun insufficientCash() {
        val cash = OrderCashSnapShot(1L, BigDecimal(1_000))
        val totalPrice = BigDecimal(1_001)

        assertThrows(IllegalStateException::class.java) {
            validator.validate(cash, totalPrice)
        }
    }
}