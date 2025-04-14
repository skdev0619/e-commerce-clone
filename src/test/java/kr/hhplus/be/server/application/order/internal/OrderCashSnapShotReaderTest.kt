package kr.hhplus.be.server.application.order.internal

import kr.hhplus.be.server.domain.cash.UserCash
import kr.hhplus.be.server.domain.cash.UserCashService
import org.assertj.core.api.Assertions.assertThat
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
class OrderCashSnapShotReaderTest {

    @Mock
    private lateinit var userCashService: UserCashService

    @InjectMocks
    private lateinit var reader: OrderCashSnapShotReader

    @DisplayName("주문 요청 당시의, 잔액 정보를 조회한다")
    @Test
    fun read() {
        val userId = 1L
        val balance = BigDecimal(10000)
        val userCash = UserCash(1L, userId, balance)

        `when`(userCashService.findByUserId(userId)).thenReturn(userCash)

        val cashSnapShot = reader.read(userId)

        assertThat(cashSnapShot.userId).isEqualTo(userId)
        assertThat(cashSnapShot.balance).isEqualTo(balance)
        verify(userCashService).findByUserId(userId)
    }
}
