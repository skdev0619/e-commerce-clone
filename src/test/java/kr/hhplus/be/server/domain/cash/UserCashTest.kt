package kr.hhplus.be.server.domain.cash

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.math.BigDecimal

class UserCashTest {

    @DisplayName("잔액 충전")
    @Nested
    inner class Charge {

        @DisplayName("잔액을 0원 이하 충전하면 예외 발생한다")
        @ValueSource(ints = [0, -1])
        @ParameterizedTest
        fun chargeException(amount: Int) {
            val userCash = UserCash(11L, 50_000)

            assertThatIllegalArgumentException()
                .isThrownBy { userCash.charge(BigDecimal(amount)) }
                .withMessage("충전 금액은 0원보다 커야 합니다")
        }

        @DisplayName("사용자의 잔액을 충전한다")
        @Test
        fun charge() {
            val userCash = UserCash(11L, 50_000)

            userCash.charge(BigDecimal(10_000))

            assertThat(userCash.balance).isEqualTo(BigDecimal(60_000))
        }
    }


    @DisplayName("잔액 사용")
    @Nested
    inner class Use {

        @DisplayName("잔액은 0원 이하 사용할 수 없다")
        @ValueSource(ints = [0, -1])
        @ParameterizedTest
        fun useByUnValidAmount(amount: Int) {
            val userCash = UserCash(11L, 1_000)

            assertThatIllegalArgumentException()
                .isThrownBy { userCash.use(BigDecimal(amount)) }
                .withMessage("사용할 금액은 0원보다 커야 합니다")
        }

        @DisplayName("사용자의 잔액을 초과하는 금액을 사용하면 예외발생한다")
        @Test
        fun useByOverAmount() {
            val userCash = UserCash(11L, 1_000)

            assertThatIllegalStateException()
                .isThrownBy { userCash.use(BigDecimal(1_001)) }
                .withMessage("잔액이 부족합니다")
        }

        @DisplayName("사용자의 잔액을 사용하면 잔액이 차감된다")
        @Test
        fun use() {
            val userCash = UserCash(11L, 1_000)

            userCash.use(BigDecimal(500))

            assertThat(userCash.balance).isEqualTo(BigDecimal(500))
        }
    }
}
