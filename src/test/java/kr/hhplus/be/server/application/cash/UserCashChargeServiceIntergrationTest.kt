package kr.hhplus.be.server.application.cash

import kr.hhplus.be.server.domain.cash.TransactionType
import kr.hhplus.be.server.domain.cash.UserCash
import kr.hhplus.be.server.domain.cash.UserCashHistoryRepository
import kr.hhplus.be.server.domain.cash.UserCashRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Transactional
@SpringBootTest
class UserCashChargeServiceIntegrationTest {

    @Autowired
    private lateinit var userCashRepository: UserCashRepository

    @Autowired
    private lateinit var userCashHistoryRepository: UserCashHistoryRepository

    @Autowired
    private lateinit var userCashChargeService: UserCashChargeService

    @DisplayName("특정 사용자의 잔액을 충전하면, 잔액이 증가하여 충전 이력이 쌓인다")
    @Test
    fun charge() {
        //given
        val userId = 17L
        val userCash = userCashRepository.save(UserCash(userId, 5_000))
        //when
        userCashChargeService.charge(userCash.userId, BigDecimal(10_000))

        //then
        val userCashView = userCashRepository.findByUserId(userCash.userId)
        val cashHistoryView = userCashHistoryRepository.findByUserId(userCash.userId)

        assertThat(userCashView)
            .extracting("userId", "balance")
            .containsExactly(userId, BigDecimal(15_000))

        assertThat(cashHistoryView)
            .extracting("userId", "type", "balance")
            .containsExactly(
                Tuple.tuple(userId, TransactionType.CHARGE, BigDecimal(10_000))
            )
    }
}