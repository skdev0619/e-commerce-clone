package kr.hhplus.be.server.application.coupon

import kr.hhplus.be.server.domain.coupon.CouponIssueRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class CouponQueryService(
    private val couponIssueRepository: CouponIssueRepository
) {
    fun findMyCoupons(userId: Long): List<MyCouponResult> {
        val coupons = couponIssueRepository.findByUserId(userId)
        return coupons.map { MyCouponResult(it.id, it.userId, it.couponId, it.status.name) }
    }
}
