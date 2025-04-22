package kr.hhplus.be.server.domain.cash

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@SpringBootTest
class UserCashServiceConcurrencyTest {
    @Autowired
    private lateinit var userCashRepository: UserCashRepository

    @Autowired
    private lateinit var userCashHistoryRepository: UserCashHistoryRepository

    @Autowired
    private lateinit var userCashService: UserCashService

    private val threadCount = 3
    private lateinit var executorService: ExecutorService
    private lateinit var latch: CountDownLatch

    @BeforeEach
    fun before() {
        executorService = Executors.newFixedThreadPool(threadCount)
        latch = CountDownLatch(threadCount)
    }

    @DisplayName("잔액을 동시에 1,000원씩 3번 충전하면 잔액은 3,000원이 된다")
    @Test
    fun charge() {
        val userId = 99L
        userCashRepository.save(UserCash(userId, 0))

        repeat(threadCount) {
            executorService.execute {
                try {
                    userCashService.charge(userId, BigDecimal(1_000))
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()
        executorService.shutdown()

        val cashInfo = userCashRepository.findByUserId(userId)
        val histories = userCashHistoryRepository.findByUserId(userId)

        assertThat(cashInfo?.balance).isEqualByComparingTo(BigDecimal(3_000))
        assertThat(histories.filter { it.type == TransactionType.CHARGE }).hasSize(3)
    }

    @DisplayName("잔액이 10,000원인 상태에서 동시에 1,000원씩 사용하면 잔액은 7,000원이 된다")
    @Test
    fun use() {
        val userId = 101L
        userCashRepository.save(UserCash(userId, 10_000))

        repeat(threadCount) {
            executorService.execute {
                try {
                    userCashService.use(userId, BigDecimal(1_000))
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()
        executorService.shutdown()

        val cashInfo = userCashRepository.findByUserId(userId)
        val histories = userCashHistoryRepository.findByUserId(userId)

        assertThat(cashInfo?.balance).isEqualByComparingTo(BigDecimal(7_000))
        assertThat(histories.filter { it.type == TransactionType.USE }).hasSize(3)
    }
}
