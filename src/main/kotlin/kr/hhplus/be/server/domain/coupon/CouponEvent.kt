package kr.hhplus.be.server.domain.coupon

class CouponEvent {
    data class tryIssued(
        val id: String,
        val couponId: Long,
        val userId: Long
    )
}