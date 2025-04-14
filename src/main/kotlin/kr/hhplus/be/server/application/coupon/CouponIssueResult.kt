package kr.hhplus.be.server.application.coupon

import kr.hhplus.be.server.domain.coupon.CouponIssue

data class CouponIssueResult(
    val id: Long,
    val userId: Long,
    val couponId: Long
) {
    companion object {
        fun from(issue: CouponIssue): CouponIssueResult {
            return CouponIssueResult(issue.id, issue.userId, issue.couponId)
        }
    }
}
