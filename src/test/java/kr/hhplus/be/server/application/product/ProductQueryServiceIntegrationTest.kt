package kr.hhplus.be.server.application.product

import kr.hhplus.be.server.domain.product.Product
import kr.hhplus.be.server.domain.product.ProductRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Transactional
@SpringBootTest
class ProductQueryServiceIntegrationTest {

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var productQueryService: ProductQueryService

    @DisplayName("상품의 식별자로 상품 조회한다")
    @Test
    fun findById() {
        val savedProduct = productRepository.save(Product("상품1", 10_000, 100))

        val result = productQueryService.findById(savedProduct.id)

        assertThat(result).extracting("name", "stock")
            .containsExactly("상품1", 100)
        assertThat(result.price).isEqualByComparingTo(BigDecimal(10_000))
    }

    @DisplayName("모든 상품 정보를 조회한다")
    @Test
    fun findAll() {
        productRepository.save(Product("상품1", 10_000, 10))
        productRepository.save(Product("상품2", 15_000, 20))

        val results = productQueryService.findAll()

        assertThat(results).extracting("name", "stock")
            .containsExactly(
                Tuple.tuple("상품1", 10),
                Tuple.tuple("상품2", 20)
            )
        assertThat(results[0].price).isEqualByComparingTo(BigDecimal(10_000))
        assertThat(results[1].price).isEqualByComparingTo(BigDecimal(15_000))
    }
}
