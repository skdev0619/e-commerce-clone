package kr.hhplus.be.server.domain.coupon

import kr.hhplus.be.server.coupon.domain.Coupon

interface CouponRepository {
    fun save(coupon : Coupon) : Coupon
    fun findById(id : Long) : Coupon?
}