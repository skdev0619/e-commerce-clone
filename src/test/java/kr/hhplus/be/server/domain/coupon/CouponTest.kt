package kr.hhplus.be.server.domain.coupon

import kr.hhplus.be.server.coupon.domain.Coupon
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalStateException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class CouponTest {

    @DisplayName("쿠폰을 특정 사용자에게 1매 발급한다")
    @Test
    fun issueCoupon() {
        val userId = 1L
        val coupon = Coupon(
            name = "10% 할인 쿠폰",
            discountType = DiscountType.PERCENTAGE,
            discountValue = 10,
            stock = 100
        )

        val issueCoupon = coupon.issue(userId)

        assertThat(coupon.stock).isEqualTo(99)
        assertThat(issueCoupon).extracting("userId", "couponId", "status")
            .containsExactly(userId, coupon.id, CouponStatus.ACTIVE)
    }

    @DisplayName("쿠폰의 재고가 0일때 발급하면 예외 발생한다")
    @Test
    fun issueCouponException() {
        val userId = 1L
        val coupon = Coupon(
            name = "5천원 할인 쿠폰",
            discountType = DiscountType.FIXED_AMOUNT,
            discountValue = 5000,
            stock = 0,
        )

        assertThatIllegalStateException()
            .isThrownBy { coupon.issue(userId) }
    }
}
