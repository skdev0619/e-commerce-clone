package kr.hhplus.be.server.domain.coupon

import kr.hhplus.be.server.coupon.domain.Coupon
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CouponServiceTest {
    private val couponRepository = FakeCouponRepository()
    private val couponIssueRepository = FakeCouponIssueRepository()
    private val availableCouponsRepository = FakeAvailableCouponsRepository()
    private val couponService = CouponService(couponRepository, couponIssueRepository, availableCouponsRepository)

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

    @DisplayName("쿠폰을 등록 시, 쿠폰 정보와 발급 가능 쿠폰 id가 같이 저장된다")
    @Test
    fun register() {
        val couponInfo = couponService.register(Coupon("10%할인", DiscountType.FIXED_AMOUNT, 10, 100))

        val coupon = couponRepository.findById(couponInfo.id)
        val isExistCouponId = availableCouponsRepository.contains(couponInfo.id)
        assertThat(coupon).extracting("name", "discountType", "discountValue", "stock")
            .containsExactly(
                "10%할인", DiscountType.FIXED_AMOUNT, 10, 100
            )
        assertThat(isExistCouponId).isTrue()
    }

    @DisplayName("쿠폰 ID가 발급 가능한 목록에 없으면 해당 쿠폰은 발급할 수 없다")
    @Test
    fun unAvailableCoupon() {
        val userId = 96L
        val couponId = 63L
        availableCouponsRepository.remove(couponId)

        assertThrows<IllegalStateException> {
            couponService.issue(userId, couponId)
        }
    }
}
