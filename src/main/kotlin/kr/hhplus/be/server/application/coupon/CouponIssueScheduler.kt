package kr.hhplus.be.server.application.coupon

import kr.hhplus.be.server.domain.coupon.CouponRequestsService
import kr.hhplus.be.server.domain.coupon.CouponService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CouponIssueScheduler(
    private val couponRequestsService: CouponRequestsService,
    private val couponService: CouponService
) {

    @Scheduled(fixedRate = 60000)   //1분마다 실행
    @Transactional
    fun consumeCouponRequests() {
        val couponIds = couponRequestsService.getCouponIds()

        couponIds.forEach { couponId ->
            couponService.findById(couponId)?.let { coupon ->
                issueCoupons(coupon.id, coupon.stock)
                couponRequestsService.removeByCouponId(couponId)
            }
        }
    }

    private fun issueCoupons(couponId: Long, stock: Int) {
        val requests = couponRequestsService.getTopRequests(couponId, stock.toLong())
        requests.userIds.forEach { userId ->
            couponService.issue(userId, couponId)
        }
    }
}
