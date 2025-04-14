package kr.hhplus.be.server.domain.coupon

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalStateException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class CouponIssueTest {

    @DisplayName("쿠폰의 직전 상태가 사용 가능이어야 사용됨으로 변경 가능하다")
    @ParameterizedTest
    @EnumSource(value = CouponStatus::class, names = ["ACTIVE"], mode = EnumSource.Mode.EXCLUDE)
    fun useException(status: CouponStatus) {
        val couponIssue = CouponIssue(1L, 10L, status)

        assertThatIllegalStateException()
            .isThrownBy { couponIssue.use() }
    }

    @DisplayName("발급된 쿠폰의 상태를 사용됨으로 변경한다")
    @Test
    fun use() {
        val couponIssue = CouponIssue(1L, 10L, CouponStatus.ACTIVE)

        couponIssue.use()

        assertThat(couponIssue.status).isEqualTo(CouponStatus.USED)
    }

    @DisplayName("쿠폰의 직전 상태가 사용 가능이어야 만료됨으로 변경 가능하다")
    @ParameterizedTest
    @EnumSource(value = CouponStatus::class, names = ["ACTIVE"], mode = EnumSource.Mode.EXCLUDE)
    fun expiredException(status: CouponStatus) {
        val couponIssue = CouponIssue(1L, 10L, status)

        assertThatIllegalStateException()
            .isThrownBy { couponIssue.expired() }
    }

    @DisplayName("발급된 쿠폰의 상태를 만료됨으로 변경한다")
    @Test
    fun expired() {
        val couponIssue = CouponIssue(1L, 10L, CouponStatus.ACTIVE)

        couponIssue.expired()

        assertThat(couponIssue.status).isEqualTo(CouponStatus.EXPIRED)
    }
}
