package kr.hhplus.be.server.application.ranking

import kr.hhplus.be.server.domain.order.*
import kr.hhplus.be.server.domain.product.Product
import kr.hhplus.be.server.domain.product.ProductRepository
import kr.hhplus.be.server.domain.ranking.BestSellingProductService
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.time.LocalDateTime

@SpringBootTest
class BestSellingProductSchedulerIntegrationTest {

    @Autowired
    private lateinit var scheduler: BestSellingProductScheduler

    @Autowired
    private lateinit var bestSellingProductService: BestSellingProductService

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var orderRepository: OrderRepository

    @DisplayName("주문 완료 데이터를 기반으로 일별 판매 집계 데이터를 생성한 뒤, 해당 집계 데이터를 바탕으로 베스트셀러 상품 정보를 저장한다")
    @Test
    fun runDailyTask() {
        //given
        val product1 = productRepository.save(Product("상품1", 15_000, 100))
        val product2 = productRepository.save(Product("상품2", 11_000, 100))
        val items = OrderItems(
            listOf(
                OrderItem(product1.id, 60, BigDecimal(15_000)),
                OrderItem(product2.id, 40, BigDecimal(11_000))
            )
        )
        val summaryDate = LocalDateTime.of(2025, 4, 21, 18, 0)
        orderRepository.save(createCompletedOrderBy(items, summaryDate))

        //when
        val baseDate = summaryDate.toLocalDate()
        scheduler.runDailyTask(baseDate.minusDays(2), baseDate)

        //then
        //최근 일자의 베스트셀러 상품 2건 조회
        val bestSellingProducts = bestSellingProductService.findBestSellingProducts(
            baseDate.minusDays(2),
            summaryDate.toLocalDate(),
            2
        )

        assertThat(bestSellingProducts).hasSize(2)
        assertThat(bestSellingProducts).extracting("productId", "salesCount", "name")
            .containsExactly(
                Tuple.tuple(product1.id, 60L, "상품1"),
                Tuple.tuple(product2.id, 40L, "상품2")
            )
    }

    private fun createCompletedOrderBy(items: OrderItems, paidDate: LocalDateTime): Order {
        return Order(
            userId = 1L,
            status = OrderStatus.PAID,
            couponIssueId = null,
            orderItems = items,
            totalPrice = items.totalPrice(),
            paidDate = paidDate
        )
    }
}