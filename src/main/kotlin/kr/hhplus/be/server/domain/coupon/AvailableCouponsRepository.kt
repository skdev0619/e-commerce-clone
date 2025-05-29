package kr.hhplus.be.server.domain.coupon

interface AvailableCouponsRepository {
    fun contains(couponId: Long): Boolean
    fun isUnavailable(couponId: Long): Boolean
    fun add(couponId: Long)
    fun remove(couponId: Long)
}