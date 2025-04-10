package kr.hhplus.be.server.domain.coupon

import org.springframework.stereotype.Service
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

@Service
class CouponIssueService(
    private val couponRepository: CouponRepository,
    private val couponIssueRepository: CouponIssueRepository
) {
    private val lock = ReentrantLock()

    fun issue(userId: Long, couponId: Long): CouponIssue {
        lock.withLock {
            val coupon = couponRepository.findById(couponId)
                ?: throw NoSuchElementException("존재하지 않는 쿠폰입니다")

            couponIssueRepository.findByUserIdAndCouponId(userId, couponId)?.let {
                throw IllegalStateException("이미 발급된 쿠폰입니다")
            }

            coupon.decreaseStock()

            val issuedCoupon = CouponIssue(userId, couponId, CouponStatus.ACTIVE)
            couponIssueRepository.save(issuedCoupon)

            return issuedCoupon
        }
    }
}
