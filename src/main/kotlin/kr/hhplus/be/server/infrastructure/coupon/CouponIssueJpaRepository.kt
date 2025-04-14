package kr.hhplus.be.server.infrastructure.coupon

import kr.hhplus.be.server.domain.coupon.CouponIssue
import kr.hhplus.be.server.domain.coupon.CouponIssueRepository
import org.springframework.stereotype.Repository

@Repository
class CouponIssueJpaRepository : CouponIssueRepository {
    override fun save(issue: CouponIssue): CouponIssue {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): CouponIssue? {
        TODO("Not yet implemented")
    }

    override fun findByUserId(userId: Long): List<CouponIssue> {
        TODO("Not yet implemented")
    }

    override fun findByUserIdAndCouponId(userId: Long, couponId: Long): CouponIssue? {
        TODO("Not yet implemented")
    }
}