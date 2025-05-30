package kr.hhplus.be.server.application.coupon

import kr.hhplus.be.server.domain.coupon.CouponEvent
import kr.hhplus.be.server.domain.coupon.CouponEventPublisher
import org.springframework.stereotype.Service

@Service
class FirstComeCouponIssueService(
    private val eventPublisher: CouponEventPublisher
) {
    fun issuedCoupon(userId: Long, couponId: Long): TryIssueCouponResult {
        eventPublisher.publish(CouponEvent.tryIssued(couponId.toString(), couponId, userId))
        return TryIssueCouponResult(couponId, userId)
    }
}
