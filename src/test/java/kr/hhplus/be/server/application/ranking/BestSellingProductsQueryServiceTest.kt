package kr.hhplus.be.server.application.ranking

import kr.hhplus.be.server.domain.ranking.BestSellingProduct
import kr.hhplus.be.server.domain.ranking.BestSellingProductRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDate

@Transactional
@SpringBootTest
class BestSellingProductsQueryServiceTest {

    @Autowired
    private lateinit var bestSellingProductRepository: BestSellingProductRepository

    @Autowired
    private lateinit var service: BestSellingProductsQueryService

    @DisplayName("특정 기간의 베스트셀러 상품 정보를 조회한다")
    @Test
    fun findBestSellingProducts() {
        val startDate = LocalDate.of(2025, 4, 21)
        val endDate = LocalDate.of(2025, 4, 23)
        bestSellingProductRepository.saveAll(
            listOf(
                BestSellingProduct(startDate, endDate, 1L, 90, "상품1", BigDecimal(15_000)),
                BestSellingProduct(startDate, endDate, 2L, 80, "상품2", BigDecimal(15_000))
            )
        )

        val bestSellingProducts = service.findBestSellingProducts(
            BestSellingProductsQuery(startDate, endDate, 2)
        )

        assertThat(bestSellingProducts).hasSize(2)
        assertThat(bestSellingProducts).extracting("productId", "salesCount", "name")
            .containsExactly(
                Tuple.tuple(1L, 90L, "상품1"),
                Tuple.tuple(2L, 80L, "상품2")
            )
    }
}