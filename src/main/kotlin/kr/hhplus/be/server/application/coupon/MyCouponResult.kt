package kr.hhplus.be.server.application.coupon

import kr.hhplus.be.server.domain.coupon.CouponIssue

data class MyCouponResult(
    val id: Long,
    val userId: Long,
    val couponId: Long,
    var status: String
) {
    companion object {
        fun from(couponIssue: CouponIssue): MyCouponResult {
            return MyCouponResult(
                couponIssue.id,
                couponIssue.userId,
                couponIssue.couponId,
                couponIssue.status.name
            )
        }
    }
}
