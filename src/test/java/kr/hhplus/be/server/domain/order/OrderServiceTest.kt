package kr.hhplus.be.server.domain.order

import kr.hhplus.be.server.domain.order.discount.FixedDiscountStrategy
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class OrderServiceTest {
    private val orderRepository = FakeOrderRepository()
    private val orderService = OrderService(orderRepository)

    @DisplayName("주문 생성하여 저장한다")
    @Test
    fun createOrder() {
        val userId = 1L
        val couponIssueId = null
        val items = OrderItems(
            listOf(
                OrderItem(productId = 1L, price = BigDecimal(10_000), quantity = 2)
            )
        )
        val discountStrategy = FixedDiscountStrategy(5_000)
        val command = OrderCommand(userId, items, couponIssueId, discountStrategy)

        val order = orderService.create(command)

        assertThat(order)
            .extracting("userId", "status", "couponIssueId", "orderItems", "totalPrice")
            .containsExactly(
                userId, OrderStatus.CREATED, couponIssueId, items, BigDecimal(15_000)
            )
    }

    @DisplayName("주문을 결제완료 상태로 변경한다")
    @Test
    fun completePayment() {
        val order = orderRepository.save(createOrderBy(OrderStatus.CREATED))

        val completeOrder = orderService.completePayment(order.id)

        assertThat(completeOrder.status).isEqualTo(OrderStatus.PAID)
    }


    private fun createOrderBy(status: OrderStatus): Order {
        val orderItems = OrderItems(listOf(OrderItem(3L, 5, BigDecimal(1_000))))
        val totalPrice = BigDecimal(5_000)

        return Order(
            userId = 1L,
            status = OrderStatus.CREATED,
            couponIssueId = null,
            orderItems = orderItems,
            totalPrice = totalPrice
        )
    }
}
