package kr.hhplus.be.server.domain.ranking

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate

class BestSellingProductServiceTest {

    private val repository = FakeBestSellingProductRepository()
    private val service = BestSellingProductService(repository)

    @DisplayName("베스트 셀러 상품을 N건 저장한다")
    @Test
    fun bulkSave() {
        val startDate = LocalDate.of(2025, 4, 21)
        val endDate = LocalDate.of(2025, 4, 23)
        val products = listOf(
            BestSellingProduct(1L, startDate, endDate, 1L, 100L, "검정버킷햇", BigDecimal(15_000)),
            BestSellingProduct(2L, startDate, endDate, 2L, 90L, "무지볼캡", BigDecimal(10_000))
        )

        service.bulkSave(products)

        val productResults = repository.findByIdIn(listOf(1L, 2L))
        assertThat(productResults).hasSize(2)
        assertThat(productResults).extracting("startDate", "endDate", "productId", "salesCount", "name", "price")
            .containsExactly(
                Tuple.tuple(startDate, endDate, 1L, 100L, "검정버킷햇", BigDecimal(15_000)),
                Tuple.tuple(startDate, endDate, 2L, 90L, "무지볼캡", BigDecimal(10_000))
            )


    }

    @DisplayName("특정 기간의 베스트셀러 상품을 N건 조회한다")
    @Test
    fun findBestSellingProducts() {
        val startDate = LocalDate.of(2025, 4, 21)
        val endDate = LocalDate.of(2025, 4, 23)
        val products = listOf(
            BestSellingProduct(1L, startDate, endDate, 1L, 100L, "검정버킷햇", BigDecimal(15_000)),
            BestSellingProduct(2L, startDate, endDate, 2L, 90L, "무지볼캡", BigDecimal(10_000))
        )
        service.bulkSave(products)

        val productResults = service.findBestSellingProducts(startDate, endDate, 2)

        assertThat(productResults).hasSize(2)
        assertThat(productResults).extracting("startDate", "endDate", "productId", "salesCount", "name", "price")
            .containsExactly(
                Tuple.tuple(startDate, endDate, 1L, 100L, "검정버킷햇", BigDecimal(15_000)),
                Tuple.tuple(startDate, endDate, 2L, 90L, "무지볼캡", BigDecimal(10_000))
            )
    }
}
