package kr.hhplus.be.server.application.coupon

import kr.hhplus.be.server.coupon.domain.Coupon
import kr.hhplus.be.server.domain.coupon.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class FirstComeCouponIssueServiceIntegrationTest {

    @Autowired
    private lateinit var couponRepository: CouponRepository

    @Autowired
    private lateinit var couponIssueRepository: CouponIssueRepository

    @Autowired
    private lateinit var firstComeCouponIssueService: FirstComeCouponIssueService

    @DisplayName("특정 사용자에게 쿠폰을 발급한다")
    @Test
    fun issueCoupon() {
        val userId = 1L
        val coupon = couponRepository.save(Coupon("1000원할인", DiscountType.FIXED_AMOUNT, 1_000, 100))

        firstComeCouponIssueService.issuedCoupon(userId, coupon.id)

        //then
        val currentCoupon = couponRepository.findById(coupon.id)
        val myCoupon = couponIssueRepository.findByUserId(userId)

        assertThat(currentCoupon?.stock).isEqualTo(99)
        assertThat(myCoupon).extracting("userId", "couponId", "status")
            .containsExactly(Tuple.tuple(userId, coupon.id, CouponStatus.ACTIVE))
    }

    @DisplayName("존재하지 않는 쿠폰 발급하면 예외 발생한다")
    @Test
    fun issueNonExistCoupon() {
        val userId = 1L
        val nonExistCouponId = 99L

        assertThrows<NoSuchElementException> {
            firstComeCouponIssueService.issuedCoupon(userId, nonExistCouponId)
        }
    }


    @DisplayName("이미 보유한 쿠폰은 발급하면 예외 발생한다")
    @Test
    fun duplicateCoupon() {
        val userId = 1L
        val couponStock = 99
        val coupon = couponRepository.save(Coupon("1000원할인", DiscountType.FIXED_AMOUNT, 1_000, couponStock))
        couponIssueRepository.save(CouponIssue(userId, coupon.id, CouponStatus.ACTIVE))

        assertThrows<IllegalStateException> {
            firstComeCouponIssueService.issuedCoupon(userId, coupon.id)
        }
    }
}
