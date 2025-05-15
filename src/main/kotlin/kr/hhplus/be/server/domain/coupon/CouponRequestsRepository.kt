package kr.hhplus.be.server.domain.coupon

import java.time.LocalDateTime

interface CouponRequestsRepository {
    fun add(couponId: Long, userId: Long, requestedAt: LocalDateTime)
    fun getTopRequests(couponId: Long, count: Long): CouponRequests
    fun getCouponIds() : List<Long>
    fun removeByCouponId(couponId: Long)
}