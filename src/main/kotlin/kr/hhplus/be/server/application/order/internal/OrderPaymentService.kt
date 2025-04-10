package kr.hhplus.be.server.application.order.internal

import kr.hhplus.be.server.application.payment.Payment
import kr.hhplus.be.server.application.payment.PaymentRepository
import kr.hhplus.be.server.domain.cash.UserCashService
import kr.hhplus.be.server.domain.coupon.CouponIssueRepository
import kr.hhplus.be.server.domain.order.OrderRepository
import kr.hhplus.be.server.domain.product.ProductService
import org.springframework.stereotype.Service

@Service
class OrderPaymentService(
    private val orderRepository: OrderRepository,
    private val couponIssueRepository: CouponIssueRepository,
    private val userCashService: UserCashService,
    private val productService: ProductService,
    private val paymentRepository: PaymentRepository
) {
    fun pay(userId: Long, orderId: Long): OrderPaymentResult {
        val order = orderRepository.findById(orderId)
            ?: throw NoSuchElementException("주문이 존재하지 않습니다")

        //잔액 사용
        userCashService.use(userId, order.totalPrice)

        //상품 재고 차감
        productService.decreaseStock(order.productQuantityPairs)

        //쿠폰 사용
        order.issueCouponId?.let {
            val coupon = couponIssueRepository.findById(it)
                ?: throw NoSuchElementException("주문이 존재하지 않습니다")
            coupon.use()
        }

        val payment = paymentRepository.save(Payment(userId, orderId, order.totalPrice))
        return OrderPaymentResult(payment.id, payment.userId, payment.orderId, payment.amount)
    }
}