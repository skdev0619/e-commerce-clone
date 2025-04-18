package kr.hhplus.be.server.coupon.domain

import kr.hhplus.be.server.domain.common.AuditInfo
import kr.hhplus.be.server.domain.coupon.CouponIssue
import kr.hhplus.be.server.domain.coupon.CouponStatus
import kr.hhplus.be.server.domain.coupon.DiscountType

class Coupon(
    val id: Long,
    val name: String,
    val discountType: DiscountType,
    val discountValue: Int,
    var stock: Int,
    val auditInfo: AuditInfo = AuditInfo()
) {
    constructor(name: String, discountType: DiscountType, discountValue: Int, stock: Int)
            : this(0L, name, discountType, discountValue, stock)

    fun issueCoupon(userId : Long) : CouponIssue {
        check(stock > 0) { "쿠폰의 재고가 부족합니다" }
        stock--
        auditInfo.update()

        return CouponIssue(
            userId = userId,
            couponId = id,
            status = CouponStatus.ACTIVE
        )
    }
}
