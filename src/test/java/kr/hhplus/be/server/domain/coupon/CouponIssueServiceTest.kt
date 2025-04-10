package kr.hhplus.be.server.domain.coupon

import kr.hhplus.be.server.coupon.domain.Coupon
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalStateException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CouponIssueServiceTest {

    private val couponRepository: CouponRepository = FakeCouponRepository()
    private val couponIssueRepository: CouponIssueRepository = FakeCouponIssueRepository()

    private val service = CouponIssueService(couponRepository, couponIssueRepository)

    @DisplayName("쿠폰을 특정 유저한테 발급한다")
    @Test
    fun issueCoupon() {
        val userId = 10L
        val couponId = 5L
        val coupon = couponRepository.save(Coupon(couponId, "10% 할인", DiscountType.PERCENTAGE, 10, 5))

        val issueCoupon = service.issue(userId, couponId)

        assertThat(coupon.stock).isEqualTo(4)
        assertThat(issueCoupon).extracting("userId", "couponId", "status")
            .containsExactly(userId, couponId, CouponStatus.ACTIVE)
    }

    @DisplayName("이미 발급된 쿠폰을 발급 받으면 예외 발생한다")
    @Test
    fun duplicatedCoupon() {
        val couponId = 1L
        val userId = 5L
        val coupon = Coupon(couponId, "10% 할인", DiscountType.PERCENTAGE, 10, 5)
        couponRepository.save(coupon)
        couponIssueRepository.save(CouponIssue(userId, couponId, CouponStatus.ACTIVE))

        assertThatIllegalStateException()
            .isThrownBy { service.issue(userId, couponId) }
    }

    @DisplayName("존재하지 않는 쿠폰이면 예외 발생한다")
    @Test
    fun nonExistCoupon() {
        assertThrows<NoSuchElementException> {
            service.issue(userId = 1L, couponId = 0L)
        }
    }
}
