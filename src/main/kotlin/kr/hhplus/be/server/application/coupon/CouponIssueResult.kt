package kr.hhplus.be.server.application.coupon

import kr.hhplus.be.server.domain.coupon.CouponIssueInfo

data class CouponIssueResult(
    val id: Long,
    val userId: Long,
    val couponId: Long,
    val name: String,
    val discountType: String,
    val discountValue: Int,
    val status: String
) {
    companion object {
        fun from(issue: CouponIssueInfo): CouponIssueResult {
            return CouponIssueResult(
                issue.id,
                issue.userId,
                issue.couponId,
                issue.name,
                issue.discountType.name,
                issue.discountValue,
                issue.status.name
            )
        }
    }
}
