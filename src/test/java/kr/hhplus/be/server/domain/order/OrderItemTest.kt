package kr.hhplus.be.server.domain.order

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class OrderItemTest{

    @DisplayName("한 상품을 여러 개 구매했을 때 총 금액을 계산한다")
    @Test
    fun amount(){
        val orderItem = OrderItem(1L, 3, BigDecimal(1_000))

        val amount = orderItem.amount()

        assertThat(amount).isEqualTo(BigDecimal(3_000))
    }
}