package kr.hhplus.be.server.interfaces.coupon.event

import kr.hhplus.be.server.domain.coupon.CouponEvent
import kr.hhplus.be.server.domain.coupon.CouponEventPublisher
import kr.hhplus.be.server.domain.coupon.CouponService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean
import org.testcontainers.shaded.org.awaitility.Awaitility.await
import java.util.concurrent.TimeUnit

@SpringBootTest
class CouponEventKafkaListenerTest {

    @MockitoSpyBean
    private lateinit var couponService: CouponService

    @Autowired
    private lateinit var eventPublisher: CouponEventPublisher

    @Test
    fun tryIssuedEventConsume() {
        val couponId = 59L
        val userId = 87L
        eventPublisher.publish(
            CouponEvent.tryIssued(
                couponId.toString(),
                couponId,
                userId
            )
        )

        await()
            .pollInterval(500L, TimeUnit.MILLISECONDS)
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted {
                verify(couponService).issue(userId, couponId)
            }
    }
}