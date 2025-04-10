package kr.hhplus.be.server.domain.product

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ProductTest {

    @DisplayName("상품의 재고 차감 시, 재고 수보다 큰 수를 차감하면 예외 발생한다")
    @Test
    fun decreaseStockByException() {
        val product = Product("검정 볼캡", 10_000, 100)

        assertThatIllegalArgumentException()
            .isThrownBy { product.decreaseStock(101) }
            .withMessage("상품의 재고가 부족합니다.")
    }

    @DisplayName("상품의 재고를 차감한다")
    @Test
    fun decreaseStock() {
        val product = Product("검정 볼캡", 10_000, 100)

        product.decreaseStock(10)

        assertThat(product.stock).isEqualTo(90)
    }
}
