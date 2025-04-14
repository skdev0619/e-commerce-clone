package kr.hhplus.be.server.domain.product

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class ProductServiceTest {
    private val productRepository = FakeProductRepository()
    private val productService = ProductService(productRepository)

    @DisplayName("상품 id-재고 쌍 목록을 전달하여 상품의 재고가 충분하지 않으면 예외 발생한다")
    @Test
    fun validateStocksByException() {
        productRepository.save(createProduct(1L, 10))
        productRepository.save(createProduct(2L, 5))

        val requestQuantities = listOf(
            ProductQuantity(1L, 11),
            ProductQuantity(2L, 5)
        )

        assertThrows<IllegalArgumentException> {
            productService.validateStock(requestQuantities)
        }
    }

    @DisplayName("상품 id-재고 쌍 목록을 전달하여 상품의 재고가 충분하면 예외 발생하지 않는다")
    @Test
    fun validateStocksByNonException() {
        productRepository.save(createProduct(1L, 10))
        productRepository.save(createProduct(2L, 5))

        val requestQuantities = listOf(
            ProductQuantity(1L, 10),
            ProductQuantity(2L, 4)
        )

        assertDoesNotThrow {
            productService.validateStock(requestQuantities)
        }
    }

    @DisplayName("상품 id-재고 쌍 목록을 전달하여 특정 상품의 재고를 감소한다")
    @Test
    fun multiDecreaseStock(){
        val product1 = productRepository.save(createProduct(1L, 10))
        val product2 = productRepository.save(createProduct(2L, 10))

        val requestQuantities = listOf(
            ProductQuantity(1L, 5),
            ProductQuantity(2L, 6)
        )

        productService.decreaseStock(requestQuantities)

        assertThat(product1.stock).isEqualTo(5)
        assertThat(product2.stock).isEqualTo(4)
    }

    private fun createProduct(id: Long, stock: Int): Product {
        return Product(id, "unknown", BigDecimal(10_000), stock)
    }
}
