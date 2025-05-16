package kr.hhplus.be.server.domain.coupon

import kr.hhplus.be.server.coupon.domain.Coupon
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CouponService(
    private val couponRepository: CouponRepository,
    private val couponIssueRepository: CouponIssueRepository
) {

    @Transactional(readOnly = true)
    fun findActiveCouponByIssueId(issueCouponId: Long): Coupon? {
        return couponIssueRepository.findById(issueCouponId)
            ?.takeIf { it.isCouponValid() }
            ?.couponId
            ?.let { couponRepository.findById(it) }
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): Coupon? {
        return couponRepository.findById(id)
    }

    @Transactional
    fun use(issueCouponId: Long) {
        val couponIssue = couponIssueRepository.findById(issueCouponId)
        couponIssue?.let {
            couponIssue.use()
        }
    }

    @Transactional
    fun issue(userId: Long, couponId: Long): CouponIssueInfo {
        val coupon = couponRepository.findByIdWithLock(couponId)
            ?: throw NoSuchElementException("존재하지 않는 쿠폰입니다")

        couponIssueRepository.findByUserIdAndCouponId(userId, couponId)?.let {
            throw IllegalStateException("이미 발급된 쿠폰입니다")
        }

        val issuedCoupon = coupon.issue(userId)

        couponIssueRepository.save(issuedCoupon)

        return CouponIssueInfo(
            issuedCoupon.id,
            userId, couponId,
            coupon.name,
            coupon.discountType,
            coupon.discountValue,
            issuedCoupon.status
        )
    }

    @Transactional(readOnly = true)
    fun findMyCoupons(userId: Long): List<CouponIssue> {
        return couponIssueRepository.findByUserId(userId)
    }
}
