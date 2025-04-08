package kr.hhplus.be.server.coupon.presentation.dto

import kr.hhplus.be.server.coupon.domain.CouponStatus
import kr.hhplus.be.server.coupon.domain.DiscountType
import java.time.LocalDateTime

data class CouponIssueDto(
    val id: Long,
    val name: String,
    val discountType: DiscountType,
    val discountValue: Int,
    val maxDiscountAmount: Int,
    val minOrderAmount: Int,
    val status : CouponStatus,
    val expiredDate: LocalDateTime
)

data class IssuedCouponResponse(
    val id: Long,
    val name: String,
    val discountType: DiscountType,
    val discountValue: Int,
    val maxDiscountAmount: Int,
    val minOrderAmount: Int,
    val expiredDate: LocalDateTime
)
