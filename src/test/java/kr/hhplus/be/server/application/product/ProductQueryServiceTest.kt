package kr.hhplus.be.server.application.product

import kr.hhplus.be.server.application.product.ProductViewResult
import kr.hhplus.be.server.domain.product.FakeProductRepository
import kr.hhplus.be.server.domain.product.Product
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class ProductQueryServiceTest {

    private val productRepository = FakeProductRepository()
    private val productService = ProductQueryService(productRepository)

    @DisplayName("존재하지 않는 상품 id로 조회 시 예외 발생한다")
    @Test
    fun findByIdException() {
        val id = 0L

        val exception = assertThrows<NoSuchElementException> {
            productService.findById(0L)
        }
        assertThat(exception.message).isEqualTo("존재하지 않는 상품입니다. id : ${id}")
    }

    @DisplayName("상품 id로 상품을 조회한다")
    @Test
    fun findById() {
        productRepository.save(Product(1L, "검정 볼캡", BigDecimal(10_000), 100))

        val product = productService.findById(1L)

        assertAll(
            { assertThat(product.id).isEqualTo(1L) },
            { assertThat(product.name).isEqualTo("검정 볼캡") },
            { assertThat(product.price).isEqualTo(BigDecimal(10_000)) },
            { assertThat(product.stock).isEqualTo(100) }
        )
    }

    @DisplayName("모든 상품들을 조회한다")
    @Test
    fun findAll() {
        productRepository.save(Product(1L, "검정 볼캡", BigDecimal(10_000), 100))
        productRepository.save(Product(2L, "회색 볼캡", BigDecimal(11_000), 50))

        val products = productService.findAll()


        assertAll(
            { assertThat(products).hasSize(2) },
            {
                assertThat(products)
                    .extracting(
                        ProductViewResult::id,
                        ProductViewResult::name,
                        ProductViewResult::price,
                        ProductViewResult::stock
                    )
                    .containsExactly(
                        Tuple.tuple(1L, "검정 볼캡", BigDecimal(10_000), 100),
                        Tuple.tuple(2L, "회색 볼캡", BigDecimal(11_000), 50)
                    )
            }
        )
    }
}
