package kr.hhplus.be.server.application.order

import kr.hhplus.be.server.domain.cash.UserCash
import kr.hhplus.be.server.domain.cash.UserCashRepository
import kr.hhplus.be.server.domain.product.Product
import kr.hhplus.be.server.domain.product.ProductRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@SpringBootTest
class OrderLockTemplateIntegrationTest {

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var userCashRepository: UserCashRepository

    @Autowired
    private lateinit var orderLockTemplate: OrderLockTemplate

    private val threadCount = 2
    private lateinit var executorService: ExecutorService
    private lateinit var latch: CountDownLatch

    @BeforeEach
    fun before() {
        executorService = Executors.newFixedThreadPool(threadCount)
        latch = CountDownLatch(threadCount)
    }

    @DisplayName("동일한 상품 주문이 동시에 요청되면, 하나의 요청만 먼저 처리되고 나머지는 요청이 처리된 후 실행된다")
    @Test
    fun executeWithLock() {
        //given
        val userId = 89L
        userCashRepository.save(UserCash(userId, 10_000))
        val product1 = productRepository.save(Product("상품1", 1_000, 100))
        val product2 = productRepository.save(Product("상품2", 1_000, 100))
        val orderItems = listOf(
            OrderItemCommand(product1.id, quantity = 1, price = 1_000),
            OrderItemCommand(product2.id, quantity = 1, price = 1_000)
        )
        val criteria = OrderCriteria(userId, orderItems, null)


        //when
        repeat(threadCount) {
            executorService.execute {
                try {
                    orderLockTemplate.createOrder(criteria)
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()
        executorService.shutdown()

        //then
        val products = productRepository.findByIdIn(listOf(product1.id, product2.id))
        assertThat(products.get(0).stock).isEqualTo(98)
        assertThat(products.get(1).stock).isEqualTo(98)

        val userCash = userCashRepository.findByUserId(userId)
        assertThat(userCash?.balance).isEqualByComparingTo(BigDecimal(6_000))
    }
}
