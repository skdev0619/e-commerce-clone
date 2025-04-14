package kr.hhplus.be.server.application.coupon

import kr.hhplus.be.server.domain.coupon.CouponIssueService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class FirstComeCouponIssueService(
    private val couponIssueService: CouponIssueService
) {
    fun issuedCoupon(userId: Long, couponId: Long): CouponIssueResult {
        val couponIssue = couponIssueService.issue(userId, couponId)
        return CouponIssueResult.from(couponIssue)
    }
}