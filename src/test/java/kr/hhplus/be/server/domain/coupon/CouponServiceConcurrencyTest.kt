package kr.hhplus.be.server.domain.coupon

import kr.hhplus.be.server.coupon.domain.Coupon
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.random.Random

@SpringBootTest
class CouponServiceConcurrencyTest {
    @Autowired
    private lateinit var couponRepository: CouponRepository

    @Autowired
    private lateinit var couponIssueRepository: CouponIssueRepository

    @Autowired
    private lateinit var couponService: CouponService

    private val threadCount = 3
    private lateinit var executorService: ExecutorService
    private lateinit var latch: CountDownLatch

    @BeforeEach
    fun before() {
        executorService = Executors.newFixedThreadPool(threadCount)
        latch = CountDownLatch(threadCount)
    }

    @DisplayName("재고가 10개인 쿠폰을 동시에 3번 발급하면 쿠폰의 재고는 7개가 된다")
    @Test
    fun issue() {
        val coupon = couponRepository.save(Coupon("1000원할인", DiscountType.FIXED_AMOUNT, 1_000, 10))

        repeat(threadCount) {
            executorService.execute {
                try {
                    val userId = Random.nextLong(1, 10_000)
                    couponService.issue(userId, coupon.id)
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()
        executorService.shutdown()

        val couponInfo = couponRepository.findById(coupon.id)
        val issueInfos = couponIssueRepository.findByCouponId(coupon.id)

        assertThat(couponInfo?.stock).isEqualTo(7)
        assertThat(issueInfos).hasSize(3)
    }
}
