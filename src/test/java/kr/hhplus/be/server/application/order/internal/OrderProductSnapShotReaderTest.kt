package kr.hhplus.be.server.application.order.internal

import kr.hhplus.be.server.domain.product.Product
import kr.hhplus.be.server.domain.product.ProductRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class OrderProductSnapShotReaderTest {

    @Mock
    private lateinit var productRepository: ProductRepository
    @InjectMocks
    private lateinit var productReader: OrderProductSnapShotReader

    @DisplayName("주문한 상품의 주문 시점 정보를 조회한다")
    @Test
    fun read() {
        // given
        val productIds = listOf(1L, 2L)
        val products = listOf(
            Product(1L, "상품1", 10_000, 100),
            Product(2L, "상품2", 20_000, 90)
        )
        `when`(productRepository.findByIdIn(productIds)).thenReturn(products)

        val productSnapShots = productReader.read(productIds)

        assertThat(productSnapShots).hasSize(2)
        assertThat(productSnapShots[0].productId).isEqualTo(1L)
        assertThat(productSnapShots[0].price).isEqualTo(BigDecimal(10_000))
        assertThat(productSnapShots[0].stock).isEqualTo(100)

        assertThat(productSnapShots[1].productId).isEqualTo(2L)
        assertThat(productSnapShots[1].price).isEqualTo(BigDecimal(20_000))
        assertThat(productSnapShots[1].stock).isEqualTo(90)
    }
}
