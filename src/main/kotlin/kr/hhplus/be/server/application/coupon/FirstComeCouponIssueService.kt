package kr.hhplus.be.server.application.coupon

import kr.hhplus.be.server.domain.coupon.CouponService
import org.springframework.stereotype.Service

@Service
class FirstComeCouponIssueService(
    private val couponService: CouponService
) {
    fun issuedCoupon(userId: Long, couponId: Long): CouponIssueResult {
        val issueResult = couponService.issue(userId, couponId)
        return CouponIssueResult.from(issueResult)
    }
}
