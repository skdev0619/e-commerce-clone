package kr.hhplus.be.server.application.order.internal

import kr.hhplus.be.server.coupon.domain.DiscountType
import kr.hhplus.be.server.domain.coupon.CouponIssueDetail
import kr.hhplus.be.server.domain.coupon.CouponStatus
import kr.hhplus.be.server.domain.coupon.IssuedCouponQueryRepository
import kr.hhplus.be.server.domain.order.OrderDiscountType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class OrderCouponSnapShotReaderTest {

    @Mock
    private lateinit var issuedCouponQueryRepository: IssuedCouponQueryRepository

    @InjectMocks
    private lateinit var couponSnapShotReader: OrderCouponSnapShotReader

    @DisplayName("유효한 쿠폰이면 쿠폰 할인 정보를 반환한다")
    @Test
    fun findActiveCouponDetail() {
        val issueCouponId = 1L
        val couponIssueDetail = CouponIssueDetail(1L, DiscountType.FIXED_AMOUNT, 1_000)

        `when`(
            issuedCouponQueryRepository
                .findCouponDetailsByIssueIdAndStatus(issueCouponId, CouponStatus.ACTIVE)
        ).thenReturn(couponIssueDetail)

        val couponSnapShot = couponSnapShotReader.read(issueCouponId)

        assertThat(couponSnapShot).isNotNull()
        assertThat(couponSnapShot)
            .extracting("couponIssueId", "discountType", "discountValue")
            .containsExactly(1L, OrderDiscountType.COUPON_FIXED, 1_000)

        verify(issuedCouponQueryRepository)
            .findCouponDetailsByIssueIdAndStatus(issueCouponId, CouponStatus.ACTIVE)
    }

    @DisplayName("존재하지 않거나, 유효하지 않은 쿠폰이면 null 반환한다")
    @Test
    fun findUnValidCoupon() {
        val issueCouponId = 1L
        `when`(
            issuedCouponQueryRepository
                .findCouponDetailsByIssueIdAndStatus(issueCouponId, CouponStatus.ACTIVE)
        ).thenReturn(null)

        val couponSnapShot = couponSnapShotReader.read(issueCouponId)

        assertThat(couponSnapShot).isNull()
    }
}