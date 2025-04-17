package kr.hhplus.be.server.domain.coupon

interface CouponIssueRepository {
    fun save(issue: CouponIssue): CouponIssue
    fun findById(id: Long): CouponIssue?
    fun findByUserId(userId: Long): List<CouponIssue>
    fun findByUserIdAndCouponId(userId: Long, couponId: Long): CouponIssue?
    fun findByCouponId(couponId: Long): List<CouponIssue>
}