package kr.hhplus.be.server.domain.order

import kr.hhplus.be.server.domain.order.discount.FixedDiscountStrategy
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import java.math.BigDecimal
import java.time.LocalDateTime

class OrderTest {

    @DisplayName("주문을 생성한다")
    @Test
    fun create() {
        val userId = 1L
        val couponIssueId = 5L
        val orderItems = OrderItems(listOf(OrderItem(3L, 5, BigDecimal(1_000))))

        val createdOrder = Order.create(userId, couponIssueId, orderItems)

        assertThat(createdOrder)
            .extracting("userId", "couponIssueId", "orderItems", "status", "totalPrice")
            .contains(userId, couponIssueId, orderItems, OrderStatus.CREATED, BigDecimal(5_000))
    }

    @DisplayName("할인을 적용하여 총 주문 금액을 계산한다")
    @Test
    fun applyDiscount() {
        val userId = 1L
        val issueCouponId = 5L
        val orderItems = OrderItems(listOf(OrderItem(3L, 1, BigDecimal(10_000))))

        val order = Order.create(userId, issueCouponId, orderItems)
        val discountStrategy = FixedDiscountStrategy(3_000)

        order.applyDiscount(discountStrategy)

        assertThat(order.totalPrice).isEqualTo(BigDecimal(7_000))
    }

    @DisplayName("주문의 상태가 주문생성(CREATED)가 아닐 때, 결제 완료로 변경하면 예외 발생한다")
    @EnumSource(value = OrderStatus::class, names = ["CREATED"], mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    fun completePaymentByStatus(status: OrderStatus) {
        val order = createOrderBy(status)

        assertThrows<IllegalStateException> {
            order.completePayment();
        }
    }

    @DisplayName("주문을 결제완료 상태로 변경한다")
    @Test
    fun completePayment() {
        val createdOrder = createOrderBy(OrderStatus.CREATED)

        createdOrder.completePayment();

        assertThat(createdOrder.status).isEqualTo(OrderStatus.PAID)
        assertThat(createdOrder.paidDate).isNotNull()
    }

    private fun createOrderBy(status: OrderStatus): Order {
        val userId = 1L
        val couponIssueId = 5L
        val orderItems = OrderItems(listOf(OrderItem(3L, 5, BigDecimal(1_000))))

        return Order(
            userId = userId,
            status = status,
            couponIssueId = couponIssueId,
            orderItems = orderItems,
            totalPrice = orderItems.totalPrice()
        )
    }
}
