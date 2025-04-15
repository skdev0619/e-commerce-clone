package kr.hhplus.be.server.application.order

import kr.hhplus.be.server.coupon.domain.Coupon
import kr.hhplus.be.server.domain.coupon.DiscountType
import kr.hhplus.be.server.domain.order.discount.FixedDiscountStrategy
import kr.hhplus.be.server.domain.order.discount.NoDiscountStrategy
import kr.hhplus.be.server.domain.order.discount.PercentDiscountStrategy
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class DiscountStrategyFactoryTest {
    private val discountStrategyFactory = DiscountStrategyFactory()

    @DisplayName("쿠폰의 할인 타입이 정액이면 정액 할인 전략을 반환한다")
    @Test
    fun fixedDiscountStrategy() {
        val coupon = mock(Coupon::class.java)
        `when`(coupon.discountType).thenReturn(DiscountType.FIXED_AMOUNT)
        `when`(coupon.discountValue).thenReturn(10_000)

        val strategy = discountStrategyFactory.create(coupon)

        assertThat(strategy).isInstanceOf(FixedDiscountStrategy::class.java)
    }

    @DisplayName("쿠폰의 할인 타입이 정률이면 정률 할인 전략을 반환한다")
    @Test
    fun percentDiscountStrategy() {
        val coupon = mock(Coupon::class.java)
        `when`(coupon.discountType).thenReturn(DiscountType.PERCENTAGE)
        `when`(coupon.discountValue).thenReturn(10)

        val strategy = discountStrategyFactory.create(coupon)

        assertThat(strategy).isInstanceOf(PercentDiscountStrategy::class.java)
    }

    @DisplayName("쿠폰이 없으면 할인 없음 전략을 반환한다")
    @Test
    fun noDiscountStrategy() {
        val coupon = mock(Coupon::class.java)
        `when`(coupon.discountType).thenReturn(null)

        val strategy = discountStrategyFactory.create(coupon)

        assertThat(strategy).isInstanceOf(NoDiscountStrategy::class.java)
    }
}
