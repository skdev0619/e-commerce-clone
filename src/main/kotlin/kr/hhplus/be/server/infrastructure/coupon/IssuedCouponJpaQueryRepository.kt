package kr.hhplus.be.server.infrastructure.coupon

import kr.hhplus.be.server.domain.coupon.CouponIssueDetail
import kr.hhplus.be.server.domain.coupon.CouponStatus
import kr.hhplus.be.server.domain.coupon.IssuedCouponQueryRepository
import org.springframework.stereotype.Repository

@Repository
class IssuedCouponJpaQueryRepository : IssuedCouponQueryRepository {
    override fun findCouponDetailsByIssueIdAndStatus(issuedId: Long, status: CouponStatus): CouponIssueDetail? {
        TODO("Not yet implemented")
    }
}