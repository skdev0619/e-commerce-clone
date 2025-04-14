package kr.hhplus.be.server.domain.coupon

/*
* 발급된 쿠폰의 할인 정보(정액, 정률 정보)
* */
data class CouponIssueDetail(
    val couponIssueId: Long,
    val discountType : DiscountType,
    val discountValue : Int
)
