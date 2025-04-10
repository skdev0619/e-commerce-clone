package kr.hhplus.be.server.interfaces.coupon

import kr.hhplus.be.server.application.coupon.MyCouponResult

data class MyCouponResponse(
    val id: Long,
    val userId: Long,
    val couponId: Long,
    val status: String
) {
    companion object {
        fun from(myCoupon: MyCouponResult): MyCouponResponse {
            return MyCouponResponse(myCoupon.id, myCoupon.userId, myCoupon.couponId, myCoupon.status)
        }
    }
}
