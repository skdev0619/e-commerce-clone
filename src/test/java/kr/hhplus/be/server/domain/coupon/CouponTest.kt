package kr.hhplus.be.server.domain.coupon

import kr.hhplus.be.server.coupon.domain.Coupon
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalStateException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class CouponTest {

    @DisplayName("쿠폰을 발급하면 쿠폰의 재고가 1 차감된다")
    @Test
    fun issueCoupon() {
        val coupon = Coupon(
            name = "10% 할인 쿠폰",
            discountType = DiscountType.PERCENTAGE,
            discountValue = 10,
            stock = 100
        )

        coupon.decreaseStock()

        assertThat(coupon.stock).isEqualTo(99)
    }

    @DisplayName("쿠폰의 재고가 0일때 발급하면 예외 발생한다")
    @Test
    fun issueCouponException() {
        // given
        val coupon = Coupon(
            name = "5천원 할인 쿠폰",
            discountType = DiscountType.FIXED_AMOUNT,
            discountValue = 5000,
            stock = 0,
        )

        assertThatIllegalStateException()
            .isThrownBy { coupon.decreaseStock() }
    }
}
