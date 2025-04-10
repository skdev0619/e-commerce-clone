import kr.hhplus.be.server.application.cash.UserCashChargeService
import kr.hhplus.be.server.domain.cash.UserCash
import kr.hhplus.be.server.domain.cash.UserCashService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class UserCashChargeServiceTest {

    @Mock
    private lateinit var userCashService: UserCashService

    private lateinit var userCashChargeService: UserCashChargeService

    @BeforeEach
    fun setUp() {
        userCashChargeService = UserCashChargeService(userCashService)
    }

    @DisplayName("잔액 충전 시 정상 결과가 반환되고 userCashService 가 호출된다")
    @Test
    fun charge() {
        val userId = 11L
        val amount = BigDecimal("10000")
        val resultCash = UserCash(1L, userId, BigDecimal(15_000))

        `when`(userCashService.charge(userId, amount)).thenReturn(resultCash)

        val result = userCashChargeService.charge(userId, amount)

        assertThat(result)
            .extracting("id", "userId", "amount", "finalBalance")
            .contains(resultCash.id, resultCash.userId, amount, result.finalBalance)

        verify(userCashService).charge(userId, amount)
    }
}
