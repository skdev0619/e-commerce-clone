package kr.hhplus.be.server.domain.coupon

data class CouponIssueInfo (
    val id : Long,
    val userId: Long,
    val couponId : Long,
    val name: String,
    val discountType: DiscountType,
    val discountValue: Int,
    val status: CouponStatus
)