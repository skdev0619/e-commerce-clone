package kr.hhplus.be.server.application.order

import kr.hhplus.be.server.coupon.domain.Coupon
import kr.hhplus.be.server.domain.cash.UserCash
import kr.hhplus.be.server.domain.cash.UserCashRepository
import kr.hhplus.be.server.domain.coupon.*
import kr.hhplus.be.server.domain.order.OrderEvent
import kr.hhplus.be.server.domain.order.OrderEventPublisher
import kr.hhplus.be.server.domain.order.OrderRepository
import kr.hhplus.be.server.domain.order.OrderStatus
import kr.hhplus.be.server.domain.payment.PaymentRepository
import kr.hhplus.be.server.domain.product.Product
import kr.hhplus.be.server.domain.product.ProductRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.NullSource
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Transactional
@SpringBootTest
class OrderFacadeIntegrationTest {

    @Autowired
    private lateinit var couponIssueRepository: CouponIssueRepository

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var couponRepository: CouponRepository

    @Autowired
    private lateinit var userCashRepository: UserCashRepository

    @Autowired
    private lateinit var orderRepository: OrderRepository

    @Autowired
    private lateinit var paymentRepository: PaymentRepository

    @MockitoBean
    private lateinit var eventPublisher: OrderEventPublisher

    @Autowired
    private lateinit var orderFacade: OrderFacade

    @DisplayName("쿠폰이 없을 경우, 할인 없이 상품의 합 금액으로 주문을 생성한다")
    @NullSource
    @ParameterizedTest
    fun order(issueCouponId: Long?) {
        //given
        val userId = 8L
        userCashRepository.save(UserCash(userId, 10_000))
        val product = productRepository.save(Product("상품1", 10_000, 100))
        val orderItems = listOf(OrderItemCommand(product.id, quantity = 1, price = 10_000))
        //when
        val createOrder = orderFacade.createOrder(OrderCriteria(userId, orderItems, issueCouponId))

        //then
        val order = orderRepository.findById(createOrder.id)
        val payment = paymentRepository.findById(createOrder.paymentId)

        //order 검증
        assertThat(order).extracting("id", "totalPrice", "status")
            .containsExactly(createOrder.id, BigDecimal(10_000), OrderStatus.PAID)

        //orderItem 검증
        assertThat(order?.orderItems?.items).extracting("productId", "quantity", "price")
            .containsExactly(
                Tuple.tuple(product.id, 1, BigDecimal(10_000))
            )
        //payment 검증
        assertThat(payment).extracting("orderId", "amount")
            .containsExactly(createOrder.id, BigDecimal(10_000))
    }

    @DisplayName("쿠폰을 적용한 주문 요청 시, 할인된 금액으로 주문을 생성한다")
    @Test
    fun orderByDiscount() {
        val userId = 45L
        userCashRepository.save(UserCash(userId, 10_000))
        val product = productRepository.save(Product("상품1", 10_000, 100))
        val orderItems = listOf(OrderItemCommand(product.id, quantity = 1, price = 10_000))
        val coupon = couponRepository.save(Coupon("1000원할인", DiscountType.FIXED_AMOUNT, 1_000, 99))
        val couponIssue = couponIssueRepository.save(CouponIssue(userId, coupon.id, CouponStatus.ACTIVE))

        //when
        val createOrder = orderFacade.createOrder(OrderCriteria(userId, orderItems, couponIssue.id))

        //then
        val order = orderRepository.findById(createOrder.id)
        val payment = paymentRepository.findById(createOrder.paymentId)

        //order 검증
        assertThat(order).extracting("id", "totalPrice", "couponIssueId", "status")
            .containsExactly(createOrder.id, BigDecimal(9_000), couponIssue.id, OrderStatus.PAID)

        //orderItem 검증
        assertThat(order?.orderItems?.items).extracting("productId", "quantity", "price")
            .containsExactly(
                Tuple.tuple(product.id, 1, BigDecimal(10_000))
            )

        //payment 검증
        assertThat(payment).extracting("orderId", "amount")
            .containsExactly(createOrder.id, BigDecimal(9_000))
    }

    @DisplayName("상품의 재고가 부족한 경우, 주문하면 예외 발생한다")
    @ValueSource(ints = [11])
    @ParameterizedTest
    fun outOfStock(productQuantityRequest: Int) {
        //given
        val userId = 9L
        val productQuantity = 10
        val product = productRepository.save(Product("상품1", 10_000, productQuantity))
        userCashRepository.save(UserCash(userId, 10_000))
        val orderItems = listOf(OrderItemCommand(product.id, quantity = productQuantityRequest, price = 10_000))

        assertThrows<IllegalStateException> {
            orderFacade.createOrder(OrderCriteria(userId, orderItems, null))
        }
    }

    @DisplayName("잔액이 부족한 경우, 주문하면 예외 발생한다")
    @ValueSource(longs = [10_001])
    @ParameterizedTest
    fun InsufficientBalance(overProductPrice: Long) {
        val userId = 7L
        val useCash = 10_000
        val product = productRepository.save(Product("상품1", 10_000, 1))
        userCashRepository.save(UserCash(userId, useCash))
        val orderItems = listOf(OrderItemCommand(product.id, quantity = 1, price = overProductPrice))

        assertThrows<IllegalStateException> {
            orderFacade.createOrder(OrderCriteria(userId, orderItems, null))
        }
    }

    @DisplayName("주문 완료되면, 주문 완료 이벤트가 발행된다")
    @Test
    fun publishCompletedEvent() {
        //given
        val userId = 97L
        val useCash = 10_000
        userCashRepository.save(UserCash(userId, useCash))
        val product = productRepository.save(Product("상품1", 10_000, 1))
        val orderItems = listOf(OrderItemCommand(product.id, quantity = 1, price = 10_000))

        //when
        val criteria = OrderCriteria(userId, orderItems, null)
        orderFacade.createOrder(criteria)

        //then
        verify(eventPublisher).publish(OrderEvent.Completed(criteria.toOrderInfo()))
    }
}
