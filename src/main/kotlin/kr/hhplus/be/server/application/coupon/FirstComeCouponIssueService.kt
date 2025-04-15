package kr.hhplus.be.server.application.coupon

import kr.hhplus.be.server.domain.coupon.CouponService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class FirstComeCouponIssueService(
    private val couponService: CouponService
) {
    fun issuedCoupon(userId: Long, couponId: Long): CouponIssueResult {
        val couponIssue = couponService.issue(userId, couponId)
        return CouponIssueResult.from(couponIssue)
    }
}