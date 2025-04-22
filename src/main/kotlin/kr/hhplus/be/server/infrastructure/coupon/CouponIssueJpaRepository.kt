package kr.hhplus.be.server.infrastructure.coupon

import kr.hhplus.be.server.domain.coupon.CouponIssue
import org.springframework.data.jpa.repository.JpaRepository

interface CouponIssueJpaRepository : JpaRepository<CouponIssue, Long> {
    fun findByUserId(id: Long): List<CouponIssue>
    fun findByUserIdAndCouponId(userId: Long, couponId: Long): CouponIssue?
    fun findByCouponId(couponId: Long): List<CouponIssue>
}