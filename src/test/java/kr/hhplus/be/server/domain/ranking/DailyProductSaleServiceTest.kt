package kr.hhplus.be.server.domain.ranking

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDate

class DailyProductSaleServiceTest {

    private val repository = FakeDailyProductSaleRepository()
    private val service = DailyProductSaleAggregateService(repository)

    @DisplayName("일별 상품별 판매 집계 정보를 N건 저장한다")
    @Test
    fun bulkSave() {
        val baseDate = LocalDate.of(2024, 4, 21)
        val dailyProductSales = listOf(
            DailyProductSale(id = 1L, baseDate = baseDate, productId = 11L, salesCount = 90L),
            DailyProductSale(id = 2L, baseDate = baseDate, productId = 21L, salesCount = 80L)
        )

        service.bulkSave(dailyProductSales)

        val result = repository.findByIdIn(listOf(1L, 2L))

        assertThat(result).hasSize(2)
        assertThat(result).extracting("id", "baseDate", "productId", "salesCount")
            .containsExactly(
                Tuple.tuple(1L, baseDate, 11L, 90L),
                Tuple.tuple(2L, baseDate, 21L, 80L)
            )
    }

    @DisplayName("특정 기간 동안 가장 많이 팔린 상품 N건을 조회한다")
    @Test
    fun findBestSellingProducts() {
        val startDate = LocalDate.of(2024, 4, 21)
        val endDate = LocalDate.of(2024, 4, 22)
        val dailyProductSales = listOf(
            DailyProductSale(id = 1L, baseDate = startDate, productId = 11L, salesCount = 90L),
            DailyProductSale(id = 2L, baseDate = startDate, productId = 21L, salesCount = 80L),
            DailyProductSale(id = 3L, baseDate = endDate, productId = 31L, salesCount = 70L),
        )
        service.bulkSave(dailyProductSales)

        val result = service.findBestSellingProducts(startDate, endDate, 3)

        assertThat(result).hasSize(3)
        assertThat(result).extracting("productId", "salesCount")
            .containsExactly(
                Tuple.tuple(11L, 90L),
                Tuple.tuple(21L, 80L),
                Tuple.tuple(31L, 70L)
            )
    }
}