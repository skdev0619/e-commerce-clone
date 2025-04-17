package kr.hhplus.be.server.application.ranking

import kr.hhplus.be.server.domain.order.Order
import kr.hhplus.be.server.domain.order.OrderItem
import kr.hhplus.be.server.domain.order.OrderItems
import kr.hhplus.be.server.domain.order.OrderRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime

@Transactional
@SpringBootTest
class TopSellingProductsQueryServiceTest {
    @Autowired
    private lateinit var orderRepository: OrderRepository
    @Autowired
    private lateinit var service: TopSellingProductsQueryService

    @DisplayName("특정 기간 사이에 가장 많이 팔린 상품 N개를 조회한다")
    @Test
    fun topSellingProducts() {
        val items = OrderItems(
            listOf(
                OrderItem(productId = 1L, quantity = 10, price = BigDecimal(1_000)),
                OrderItem(productId = 2L, quantity = 9, price = BigDecimal(1_000)),
                OrderItem(productId = 3L, quantity = 8, price = BigDecimal(1_000)),
                OrderItem(productId = 4L, quantity = 7, price = BigDecimal(1_000)),
                OrderItem(productId = 5L, quantity = 6, price = BigDecimal(1_000)),
                OrderItem(productId = 6L, quantity = 5, price = BigDecimal(1_000))
            )
        )
        orderRepository.save(completeOrder(items))
        val query = TopSellingProductsQuery(
            startDate = LocalDateTime.now().minusDays(1),
            endDate = LocalDateTime.now().plusDays(1),
            limit = 5
        )

        val topSellingProducts = service.findTopSellingProducts(query)

        assertThat(topSellingProducts).hasSize(5)
        assertThat(topSellingProducts).extracting("productId", "sales")
            .containsExactlyInAnyOrder(
                Tuple.tuple(1L, 10L),
                Tuple.tuple(2L, 9L),
                Tuple.tuple(3L, 8L),
                Tuple.tuple(4L, 7L),
                Tuple.tuple(5L, 6L)
            )
    }

    private fun completeOrder(orderItems: OrderItems): Order {
        val order = Order.create(56L, null, orderItems)
        order.completePayment()
        return order
    }
}