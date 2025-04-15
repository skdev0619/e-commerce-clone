package kr.hhplus.be.server.application.order

import kr.hhplus.be.server.coupon.domain.Coupon
import kr.hhplus.be.server.domain.coupon.DiscountType
import kr.hhplus.be.server.domain.order.discount.DiscountStrategy
import kr.hhplus.be.server.domain.order.discount.FixedDiscountStrategy
import kr.hhplus.be.server.domain.order.discount.NoDiscountStrategy
import kr.hhplus.be.server.domain.order.discount.PercentDiscountStrategy
import org.springframework.stereotype.Component

@Component
class DiscountStrategyFactory {
    fun create(coupon: Coupon?): DiscountStrategy {

        return when (coupon?.discountType) {
            DiscountType.FIXED_AMOUNT -> FixedDiscountStrategy(coupon.discountValue)
            DiscountType.PERCENTAGE -> PercentDiscountStrategy(coupon.discountValue)
            else -> NoDiscountStrategy()
        }
    }
}
