package kr.hhplus.be.server.domain.coupon

import kr.hhplus.be.server.coupon.domain.Coupon
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CouponService(
    private val couponRepository: CouponRepository,
    private val couponIssueRepository: CouponIssueRepository,
    private val availableCouponsRepository: AvailableCouponsRepository
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
    fun register(coupon: Coupon): Coupon {
        val coupon = couponRepository.save(coupon)
        //쿠폰 등록 시, 발급 가능한 쿠폰 목록에 추가
        availableCouponsRepository.add(coupon.id)
        return coupon
    }

    @Transactional
    fun issue(userId: Long, couponId: Long): CouponIssueInfo {
        if (availableCouponsRepository.isUnavailable(couponId)) {
            throw IllegalStateException("발급할 수 없는 쿠폰입니다")
        }

        val coupon = couponRepository.findByIdWithLock(couponId)
            ?: throw NoSuchElementException("존재하지 않는 쿠폰입니다")

        couponIssueRepository.findByUserIdAndCouponId(userId, couponId)?.let {
            throw IllegalStateException("이미 발급된 쿠폰입니다")
        }

        val issuedCoupon = try {
            coupon.issue(userId)
        } catch (e: IllegalStateException) {
            //쿠폰 재고 없으면, 발급 가능한 쿠폰 id 목록에서 제거
            availableCouponsRepository.remove(couponId)
            throw e
        }

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
