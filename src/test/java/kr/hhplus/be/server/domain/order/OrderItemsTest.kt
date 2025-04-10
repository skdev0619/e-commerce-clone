package kr.hhplus.be.server.domain.order

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class OrderItemsTest {

    @DisplayName("주문 내역의 총 주문 금액을 계산한다")
    @Test
    fun totalPrice() {
        val items = OrderItems(
            listOf(
                OrderItem(1L, 5, BigDecimal(1_000)),
                OrderItem(2L, 3, BigDecimal(10_000))
            )
        )

        val totalPrice = items.totalPrice()

        assertThat(totalPrice).isEqualTo(BigDecimal(3_5000))
    }

    @DisplayName("주문 항목의 상품 id, 상품 수량의 쌍을 생성한다")
    @Test
    fun productPairs() {
        val items = listOf(
            OrderItem(productId = 1L, quantity = 3, price = BigDecimal(1_000)),
            OrderItem(productId = 2L, quantity = 5, price = BigDecimal(2_000))
        )
        val orderItems = OrderItems(items)

        val pairs = orderItems.productQuantityPairs

        assertThat(pairs).isEqualTo(listOf(1L to 3, 2L to 5))
    }
}
