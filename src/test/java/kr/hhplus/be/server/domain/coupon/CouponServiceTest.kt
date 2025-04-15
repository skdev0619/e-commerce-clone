package kr.hhplus.be.server.domain.coupon

import kr.hhplus.be.server.coupon.domain.Coupon
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class CouponServiceTest {
    private val couponRepository = FakeCouponRepository()
    private val couponIssueRepository = FakeCouponIssueRepository()
    private val couponService = CouponService(couponRepository, couponIssueRepository)

    @DisplayName("유효한 쿠폰 발급 ID로 유효한 쿠폰 정보를 조회한다")
    @Test
    fun findActiveCouponByIssueId() {
        val couponId = 1L
        val couponIssueId = 5L
        couponRepository.save(Coupon(couponId, "10%할인", DiscountType.FIXED_AMOUNT, 10, 100))
        couponIssueRepository.save(CouponIssue(couponIssueId, 1L, couponId, CouponStatus.ACTIVE))

        val coupon = couponService.findActiveCouponByIssueId(couponIssueId)

        assertThat(coupon).isNotNull()
        assertThat(coupon).extracting(
            "id", "name", "discountType", "discountValue", "stock"
        ).containsExactly(
            couponId, "10%할인", DiscountType.FIXED_AMOUNT, 10, 100
        )
    }

    @DisplayName("발급받은 쿠폰을 사용한다")
    @Test
    fun use() {
        val couponId = 1L
        val couponIssueId = 5L
        couponRepository.save(Coupon(couponId, "10%할인", DiscountType.FIXED_AMOUNT, 10, 100))
        val couponIssue = couponIssueRepository.save(CouponIssue(couponIssueId, 1L, couponId, CouponStatus.ACTIVE))

        couponService.use(couponIssueId)

        assertThat(couponIssue.status).isEqualTo(CouponStatus.USED)
    }
}
