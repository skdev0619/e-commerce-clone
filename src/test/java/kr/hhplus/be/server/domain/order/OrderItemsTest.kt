package kr.hhplus.be.server.domain.order

import kr.hhplus.be.server.domain.order.discount.FixedDiscountStrategy
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class OrderItemsTest {

    @DisplayName("주문 내역의 주문 금액의 총 합을 계산한다")
    @Test
    fun originTotalPrice() {
        val items = OrderItems(
            listOf(
                OrderItem(1L, 5, BigDecimal(1_000)),
                OrderItem(2L, 3, BigDecimal(10_000))
            )
        )

        val totalPrice = items.originTotalPrice()

        assertThat(totalPrice).isEqualTo(BigDecimal(3_5000))
    }

    @DisplayName("할인 적용하여 총 주문 금액을 계산한다")
    @Test
    fun calculatePrice(){
        val items = OrderItems(
            listOf(
                OrderItem(1L, 1, BigDecimal(10_000)),
            )
        )
        val discountStrategy = FixedDiscountStrategy(5_000)

        val price = items.calculatePrice(discountStrategy)

        assertThat(price).isEqualTo(BigDecimal(5_000))

    }


}
