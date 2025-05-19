package kr.hhplus.be.server.application.coupon

import kr.hhplus.be.server.coupon.domain.Coupon
import kr.hhplus.be.server.domain.coupon.CouponIssueRepository
import kr.hhplus.be.server.domain.coupon.CouponRepository
import kr.hhplus.be.server.domain.coupon.CouponRequestsRepository
import kr.hhplus.be.server.domain.coupon.DiscountType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class CouponIssueSchedulerIntegrationTest {

    @Autowired
    private lateinit var couponRepository: CouponRepository

    @Autowired
    private lateinit var couponIssueRepository: CouponIssueRepository

    @Autowired
    private lateinit var couponRequestsRepository: CouponRequestsRepository

    @Autowired
    private lateinit var scheduler: CouponIssueScheduler

    @DisplayName("쿠폰 요청 대기열에 3건의 요청이 있지만, 쿠폰 재고가 2개여서 2명에게만 쿠폰 발급된다")
    @Test
    fun issueCoupon() {
        //given - 쿠폰 재고 2개
        val coupon = couponRepository.save(createCoupon(2))
        //쿠폰 발급 요청은 3개 insert
        couponRequestsRepository.add(coupon.id, 756L, LocalDateTime.now())
        couponRequestsRepository.add(coupon.id, 757L, LocalDateTime.now())
        couponRequestsRepository.add(coupon.id, 758L, LocalDateTime.now())

        //when
        scheduler.consumeCouponRequests()

        //then - 쿠폰의 재고는 0개
        val couponInfo = couponRepository.findById(coupon.id)
        assertThat(couponInfo?.stock).isZero()

        val issueCoupons = couponIssueRepository.findByCouponId(coupon.id)
        assertThat(issueCoupons).hasSize(2)
    }

    private fun createCoupon(stock: Int): Coupon {
        return Coupon("unknown", DiscountType.PERCENTAGE, 10, stock)
    }
}
