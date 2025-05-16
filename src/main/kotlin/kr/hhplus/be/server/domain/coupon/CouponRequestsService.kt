package kr.hhplus.be.server.domain.coupon

import org.springframework.stereotype.Service
import java.time.LocalDateTime

/*
* 쿠폰 요청을 큐잉 시스템으로 관리할 경우, 아래 서비스 사용
* */
@Service
class CouponRequestsService(
    private val couponRequestsRepository: CouponRequestsRepository
) {

    fun add(couponId: Long, userId: Long, requestedAt: LocalDateTime) {
        return couponRequestsRepository.add(couponId, userId, requestedAt)
    }

    fun getTopRequests(couponId: Long, count: Long): CouponRequests {
        return couponRequestsRepository.getTopRequests(couponId, count)
    }

    fun getCouponIds(): List<Long> {
        return couponRequestsRepository.getCouponIds()
    }

    fun removeByCouponId(couponId: Long) {
        couponRequestsRepository.removeByCouponId(couponId)
    }
}
