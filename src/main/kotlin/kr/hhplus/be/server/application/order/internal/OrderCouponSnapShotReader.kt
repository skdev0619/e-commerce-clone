package kr.hhplus.be.server.application.order.internal

import kr.hhplus.be.server.coupon.domain.DiscountType
import kr.hhplus.be.server.domain.coupon.CouponStatus
import kr.hhplus.be.server.domain.coupon.IssuedCouponQueryRepository
import kr.hhplus.be.server.domain.order.OrderCouponSnapShot
import kr.hhplus.be.server.domain.order.OrderDiscountType
import org.springframework.stereotype.Component

/*
* 주문 생성 당시의, 쿠폰 정보 조회
* 주문 도메인의 OrderCouponSnapShot VO를 리턴
* 유효한 쿠폰이 아니거나 쿠폰이 없으면 null 리턴
* */

@Component
class OrderCouponSnapShotReader(
    private val issuedCouponQueryRepository: IssuedCouponQueryRepository
) {
    fun read(issueCouponId: Long): OrderCouponSnapShot? {
        val couponIssueDetail = issuedCouponQueryRepository
            .findCouponDetailsByIssueIdAndStatus(issueCouponId, CouponStatus.ACTIVE)

        return couponIssueDetail?.let {
            OrderCouponSnapShot(
                issueCouponId,
                toOrderDiscountType(it.discountType),
                it.discountValue
            )
        }
    }

    private fun toOrderDiscountType(type: DiscountType): OrderDiscountType {
        return when (type) {
            DiscountType.FIXED_AMOUNT -> OrderDiscountType.COUPON_FIXED
            DiscountType.PERCENTAGE -> OrderDiscountType.COUPON_RATE
        }
    }
}

