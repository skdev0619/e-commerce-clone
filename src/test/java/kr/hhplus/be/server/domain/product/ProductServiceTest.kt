package kr.hhplus.be.server.domain.product

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ProductServiceTest {
    private val productRepository = FakeProductRepository()
    private val productService = ProductService(productRepository)

    @DisplayName("상품의 id-상품의 수량 쌍을 입력하여 상품의 재고를 차감한다")
    @Test
    fun decreaseStock() {
        productRepository.save(Product(1L, "상품1", BigDecimal(1_000), 100))

        productService.decreaseStock(listOf(1L to 10))

        val stock = productRepository.findById(1L)?.stock
        assertThat(stock).isEqualTo(90)
    }
}
