package kr.hhplus.be.server.application.coupon

import kr.hhplus.be.server.application.lock.DistributedLockManager
import kr.hhplus.be.server.domain.coupon.CouponService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class FirstComeCouponIssueService(
    @Qualifier("lettuceDistributedLockManager")
    private val lockManager: DistributedLockManager,
    private val couponService: CouponService
) {

    companion object {
        private const val DEFAULT_WAIT_TIME_MILLIS = 60000L   // 락을 시도할 최대 대기 시간
        private const val DEFAULT_LEASE_TIME_MILLIS = 30000L  // 락 점유 시간 (TTL)
    }

    fun issuedCoupon(userId: Long, couponId: Long): CouponIssueResult {
        val lockKey = "coupon-issue-:${couponId}"

        val issueResult = lockManager.executeWithLock(
            lockKey,
            DEFAULT_WAIT_TIME_MILLIS,
            DEFAULT_LEASE_TIME_MILLIS,
            TimeUnit.MILLISECONDS
        ) { couponService.issue(userId, couponId) }

        return CouponIssueResult.from(issueResult)
    }
}
