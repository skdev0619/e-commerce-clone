package kr.hhplus.be.server.domain.order

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class OrderTotalPriceCalculatorTest {

    private val priceCalculator = OrderTotalPriceCalculator()

    @DisplayName("쿠폰이 없으면 할인 없이 총 주문 금액을 구한다")
    @Test
    fun totalPrice() {
        val orderItems = OrderItems(
            listOf(OrderItem(1L, 2, BigDecimal(10_000)))
        )

        val totalPrice = priceCalculator.calculateTotalPrice(orderItems, null)

        assertThat(totalPrice).isEqualTo(BigDecimal(20_000))
    }

    @DisplayName("정액 할인 쿠폰이 적용되면 금액이 차감하여 계산한다")
    @Test
    fun discountByFixedAmountCoupon() {
        val orderItems = OrderItems(
            listOf(OrderItem(1L, 1, BigDecimal(10_000)))
        )
        val coupon = OrderCouponSnapShot(1L, OrderDiscountType.COUPON_FIXED, 1_000)

        val totalPrice = priceCalculator.calculateTotalPrice(orderItems, coupon)

        assertThat(totalPrice).isEqualTo(BigDecimal(9_000))
    }

    @DisplayName("정률 할인 쿠폰이 적용되면 비율만큼 차감하여 계산한다")
    @Test
    fun discountByRateCoupon() {
        val orderItems = OrderItems(listOf(OrderItem(1L, 1, BigDecimal(10_000))))
        val coupon = OrderCouponSnapShot(1L, OrderDiscountType.COUPON_RATE, 10)

        val totalPrice = priceCalculator.calculateTotalPrice(orderItems, coupon)

        assertThat(totalPrice).isEqualTo(BigDecimal(9_000))
    }

    @DisplayName("할인 금액이 총 주문금액보다 크면 0원으로 계산한다")
    @Test
    fun discountByOver() {
        val orderItems = OrderItems(
            listOf(OrderItem(1L, 1, BigDecimal(10_000)))
        )
        val coupon = OrderCouponSnapShot(1L, OrderDiscountType.COUPON_FIXED, 10_001)

        val totalPrice = priceCalculator.calculateTotalPrice(orderItems, coupon)

        assertThat(totalPrice).isZero()
    }
}