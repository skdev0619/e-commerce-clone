package kr.hhplus.be.server.domain.order

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.math.BigDecimal

class OrderProductValidatorTest {

    private val productValidator = OrderProductValidator()

    @DisplayName("상품의 재고가 주문 수량보다 크거나 같으면 주문이 가능하다")
    @ParameterizedTest
    @ValueSource(ints = [10, 9])
    fun validate_success(stock: Int) {
        val productId = 1L
        val products = listOf(
            OrderProductSnapShot(productId, BigDecimal(1_000), 10)
        )

        val orderItems = OrderItems(
            listOf(
                OrderItem(productId, stock, BigDecimal(1_000))
            )
        )

        assertDoesNotThrow {
            productValidator.validate(products, orderItems)
        }
    }

    @DisplayName("상품의 재고가 주문 수량보다 작으면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(ints = [11])
    fun insufficientStock(stock : Int) {
        val productId = 1L
        val products = listOf(
            OrderProductSnapShot(productId, BigDecimal(1_000), 10)
        )

        val orderItems = OrderItems(
            listOf(
                OrderItem(productId, stock, BigDecimal(1_000))
            )
        )

        assertThrows(IllegalStateException::class.java) {
            productValidator.validate(products, orderItems)
        }
    }

    @Test
    @DisplayName("존재하지 않는 상품을 주문하면 예외가 발생한다")
    fun validateNonExistProduct() {
        val nonExistProductId = 0L
        val products = listOf(
            OrderProductSnapShot(1L, BigDecimal(1_000), 10)
        )

        val orderItems = OrderItems(
            listOf(
                OrderItem(nonExistProductId, 10, BigDecimal(1_000))
            )
        )

        assertThrows(NoSuchElementException::class.java) {
            productValidator.validate(products, orderItems)
        }
    }
}