package kr.hhplus.be.server.domain.coupon

import java.util.concurrent.ConcurrentHashMap

class FakeCouponIssueRepository : CouponIssueRepository {
    private val couponIssues = ConcurrentHashMap<Long, CouponIssue>()

    override fun save(issue: CouponIssue): CouponIssue {
        couponIssues.put(issue.id, issue)
        return issue
    }

    override fun findById(id: Long): CouponIssue? {
        return couponIssues.get(id)
    }

    override fun findByUserId(userId: Long): List<CouponIssue> {
        return couponIssues.values.filter { it.userId == userId }
    }

    override fun findByUserIdAndCouponId(userId: Long, couponId: Long): CouponIssue? {
        return couponIssues.values.find { it.userId == userId && it.couponId == couponId }
    }

    override fun findByCouponId(couponId: Long): List<CouponIssue> {
        return couponIssues.values.filter { it.couponId == couponId }
    }
}