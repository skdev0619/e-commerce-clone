package kr.hhplus.be.server.application.cash

import kr.hhplus.be.server.domain.cash.UserCash
import kr.hhplus.be.server.domain.cash.UserCashRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class UserCashQueryServiceIntegrationTest {

    @Autowired
    private lateinit var userCashRepository: UserCashRepository

    @Autowired
    private lateinit var userCashQueryService: UserCashQueryService

    @DisplayName("특정 유저의 현재 잔액을 조회한다")
    @Test
    fun getBalance() {
        val userId = 196L
        userCashRepository.save(UserCash(userId, 5_000))

        val userCashView = userCashQueryService.findByUserId(userId)

        val userCash = userCashRepository.findByUserId(userId)
        assertThat(userCashView.userId).isEqualTo(userCash?.userId)
        assertThat(userCashView.balance).isEqualTo(userCash?.balance)
    }
}
