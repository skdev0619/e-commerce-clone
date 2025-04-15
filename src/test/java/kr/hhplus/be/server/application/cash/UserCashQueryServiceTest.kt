package kr.hhplus.be.server.application.cash

import kr.hhplus.be.server.domain.cash.UserCash
import kr.hhplus.be.server.domain.cash.UserCashService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class UserCashQueryServiceTest {

    @Mock
    private lateinit var userCashService: UserCashService

    @InjectMocks
    private lateinit var userCashQueryService: UserCashQueryService

    @DisplayName("특정 사용자의 잔액 조회 시 정상 결과가 반환되고 userCashService 가 호출된다")
    @Test
    fun charge() {
        val userId = 11L
        val resultCash = UserCash(1L, userId, BigDecimal(15_000))

        `when`(userCashService.findByUserId(userId)).thenReturn(resultCash)

        val result = userCashQueryService.findByUserId(userId)

        assertThat(result)
            .extracting("id", "userId", "balance")
            .contains(resultCash.id, resultCash.userId, result.balance)

        verify(userCashService).findByUserId(userId)
    }
}
