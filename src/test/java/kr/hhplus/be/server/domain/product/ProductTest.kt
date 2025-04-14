package kr.hhplus.be.server.domain.product

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ProductTest {

    @DisplayName("상품의 재고 검증 시, 요청한 양보다 상품의 재고가 적으면 예외 발생한다")
    @Test
    fun validateStock() {
        val product = Product("검정 볼캡", 10_000, 100)

        assertThrows<IllegalArgumentException> {
            product.validateStock(101)
        }
    }

    @DisplayName("상품의 재고 검증 시, 요청한 양보다 상품의 재고가 같거나 크면 예외 발생하지 않는단")
    @ValueSource(ints = [99, 100])
    @ParameterizedTest
    fun validateStockByException(quantity: Int) {
        val product = Product("검정 볼캡", 10_000, 100)

        assertDoesNotThrow {
            product.validateStock(quantity)
        }
    }

    @DisplayName("상품의 재고 차감 시, 재고 수보다 큰 수를 차감하면 예외 발생한다")
    @Test
    fun decreaseStockByException() {
        val product = Product("검정 볼캡", 10_000, 100)

        assertThrows<IllegalArgumentException> {
            product.decreaseStock(101)
        }
    }

    @DisplayName("상품의 재고를 차감한다")
    @Test
    fun decreaseStock() {
        val product = Product("검정 볼캡", 10_000, 100)

        product.decreaseStock(10)

        assertThat(product.stock).isEqualTo(90)
    }
}
