package kr.hhplus.be.server.domain.cash

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class UserCashServiceTest {

    private val userCashRepository = FakeUserCashRepository()
    private val userHistoryRepository = FakeUserCashHistoryRepository()

    private val userCashService = UserCashService(userCashRepository, userHistoryRepository)

    @DisplayName("사용자별 잔액 정보가 없으면 조회 시 예외 발생한다")
    @Test
    fun findByUserIdException() {
        val exception = assertThrows<NoSuchElementException> {
            userCashService.findByUserId(0L)
        }
        assertThat(exception.message).isEqualTo("해당 사용자의 잔액이 존재하지 않습니다")
    }

    @DisplayName("사용자 id로 사용자별 잔액 정보를 조회한다")
    @Test
    fun findByUserId(){
        userCashRepository.save(UserCash(1L, 15_000))

        val userCash = userCashService.findByUserId(1L)

        assertThat(userCash).extracting(UserCash::userId, UserCash::balance)
            .containsExactly(1L, BigDecimal(15_000))
    }

    @DisplayName("잔액을 충전하면 잔액이 증가하고 잔액 충전 이력이 쌓인다")
    @Test
    fun charge() {
        userCashRepository.save(UserCash(1L, 10_000))

        val userCash = userCashService.charge(1L, BigDecimal(5_000))

        assertThat(userCash)
            .extracting(UserCash::userId, UserCash::balance)
            .containsExactly(1L, BigDecimal(15_000))

        val cashHistory = userHistoryRepository.findByUserId(1L).first()
        assertThat(cashHistory)
            .extracting(UserCashHistory::userId, UserCashHistory::type, UserCashHistory::balance)
            .containsExactly(1L, TransactionType.CHARGE, BigDecimal(5_000))
    }


    @DisplayName("잔액을 사용하면 잔액이 증가하고 잔액 사용 이력이 쌓인다")
    @Test
    fun use() {
        userCashRepository.save(UserCash(1L, 10_000))

        val userCash = userCashService.use(1L, BigDecimal(5_000))

        assertThat(userCash)
            .extracting(UserCash::userId, UserCash::balance)
            .containsExactly(1L, BigDecimal(5_000))

        val cashHistory = userHistoryRepository.findByUserId(1L).first()
        assertThat(cashHistory)
            .extracting(UserCashHistory::userId, UserCashHistory::type, UserCashHistory::balance)
            .containsExactly(1L, TransactionType.USE, BigDecimal(5_000))
    }
}
