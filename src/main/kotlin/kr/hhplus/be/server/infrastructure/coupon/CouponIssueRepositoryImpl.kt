package kr.hhplus.be.server.infrastructure.coupon

import kr.hhplus.be.server.domain.coupon.CouponIssue
import kr.hhplus.be.server.domain.coupon.CouponIssueRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class CouponIssueRepositoryImpl(
    private val jpaRepository: CouponIssueJpaRepository
) : CouponIssueRepository {

    override fun save(issue: CouponIssue): CouponIssue {
        return jpaRepository.save(issue)
    }

    override fun findById(id: Long): CouponIssue? {
        return jpaRepository.findByIdOrNull(id)
    }

    override fun findByUserId(userId: Long): List<CouponIssue> {
        return jpaRepository.findByUserId(userId)
    }

    override fun findByUserIdAndCouponId(userId: Long, couponId: Long): CouponIssue? {
        return jpaRepository.findByUserIdAndCouponId(userId, couponId)
    }

    override fun findByCouponId(couponId: Long): List<CouponIssue> {
        return jpaRepository.findByCouponId(couponId)
    }
}
