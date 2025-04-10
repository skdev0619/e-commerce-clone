package kr.hhplus.be.server.domain.order

import org.springframework.stereotype.Component
import java.math.BigDecimal

/*
* 주문의 총 주문 금액을 계산하는 클래스
- 쿠폰이 있으면 할인 하여 계산처리한다
* * */

@Component
class OrderTotalPriceCalculator {

    fun calculateTotalPrice(orderItems: OrderItems, coupon : OrderCouponSnapShot?): BigDecimal {
        val totalPrice = orderItems.totalPrice()

        return coupon?.let {
            it.discountType.calculate(totalPrice, it.discountValue)
        } ?: totalPrice
    }
}