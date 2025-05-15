package kr.hhplus.be.server.application.ranking

import kr.hhplus.be.server.domain.product.Product
import kr.hhplus.be.server.domain.product.ProductRepository
import kr.hhplus.be.server.domain.ranking.DailyProductSaleRankingRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
class DailySellingProductsQueryServiceTest {

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var dailySalesRepository: DailyProductSaleRankingRepository

    @Autowired
    private lateinit var dailySellingProductsQueryService: DailySellingProductsQueryService

    @DisplayName("일별 베스트셀러 상품을 조회한다")
    @Test
    fun getDailyRanking() {
        //given
        val product1 = productRepository.save(Product("상품1", 20_000, 100))
        val product2 = productRepository.save(Product("상품2", 10_000, 100))

        val baseDate = LocalDate.of(2025, 5, 7)
        dailySalesRepository.add(baseDate, product1.id, 10L)
        dailySalesRepository.add(baseDate, product2.id, 9L)

        //when
        val ranking = dailySellingProductsQueryService.getRanking(baseDate, 2)

        //then
        assertThat(ranking).extracting("productId", "name", "salesCount")
            .containsExactly(
                Tuple.tuple(product1.id, "상품1", 10L),
                Tuple.tuple(product2.id, "상품2", 9L),
            )
    }
}
