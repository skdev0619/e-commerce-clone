package kr.hhplus.be.server.domain.product

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@SpringBootTest
class ProductServiceConcurrencyTest {
    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var productService: ProductService

    private val threadCount = 3
    private lateinit var executorService: ExecutorService
    private lateinit var latch: CountDownLatch

    @BeforeEach
    fun before() {
        executorService = Executors.newFixedThreadPool(threadCount)
        latch = CountDownLatch(threadCount)
    }

    @DisplayName("재고가 10개인 상품을 동시에 1개씩 3번 감소시키면, 재고는 7개가 된다")
    @Test
    fun decreaseStock() {
        val product = productRepository.save(Product("상품1", 5_000, 10))
        val productQuantities = listOf(ProductQuantity(productId = product.id, quantity = 1))

        repeat(threadCount) {
            executorService.execute {
                try {
                    productService.decreaseStock(productQuantities)
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()
        executorService.shutdown()

        val productInfo = productRepository.findById(product.id)
        assertThat(productInfo?.stock).isEqualTo(7)
    }
}
