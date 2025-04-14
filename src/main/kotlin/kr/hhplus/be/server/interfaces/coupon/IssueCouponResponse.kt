package kr.hhplus.be.server.interfaces.coupon

import kr.hhplus.be.server.application.coupon.CouponIssueResult

data class IssueCouponResponse(
    val id: Long,
    val userId: Long,
    val couponId: Long
) {
    companion object {
        fun from(issue: CouponIssueResult): IssueCouponResponse {
            return IssueCouponResponse(issue.id, issue.userId, issue.couponId)
        }
    }
}
