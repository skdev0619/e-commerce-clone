package kr.hhplus.be.server.application.coupon

import kr.hhplus.be.server.domain.coupon.CouponService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class CouponQueryService(
    private val couponService: CouponService
) {
    fun findMyCoupons(userId: Long): List<MyCouponResult> {
        val coupons = couponService.findMyCoupons(userId)
        return coupons.map { MyCouponResult(it.id, it.userId, it.couponId, it.status.name) }
    }
}
