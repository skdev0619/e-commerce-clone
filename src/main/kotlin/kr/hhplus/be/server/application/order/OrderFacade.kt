package kr.hhplus.be.server.application.order

import kr.hhplus.be.server.domain.payment.Payment
import kr.hhplus.be.server.domain.payment.PaymentService
import kr.hhplus.be.server.domain.cash.UserCashService
import kr.hhplus.be.server.domain.coupon.CouponService
import kr.hhplus.be.server.domain.order.OrderService
import kr.hhplus.be.server.domain.product.ProductService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class OrderFacade(
    private val productService: ProductService,
    private val couponService: CouponService,
    private val userCashService: UserCashService,
    private val discountStrategyFactory: DiscountStrategyFactory,
    private val orderService: OrderService,
    private val paymentService: PaymentService
) {
    fun createOrder(criteria: OrderCriteria): OrderCompletedResult {
        //1. 재고 검증
        productService.validateStock(criteria.toProductQuantities())

        //2. 유효한 쿠폰 조회
        val coupon = criteria.issueCouponId?.let {
            couponService.findActiveCouponByIssueId(it)
        }

        //3. 할인 전락 구현체 조회
        val discountStrategy = discountStrategyFactory.create(coupon)

        //4. 주문 생성
        val order = orderService.create(criteria.toOrderCommand(discountStrategy))

        //5. 금액 차감
        userCashService.use(criteria.userId, order.totalPrice)

        //6. 재고 감소
        productService.decreaseStock(criteria.toProductQuantities())

        //7. 쿠폰 있으면 쿠폰 사용
        coupon?.let {
            couponService.use(criteria.issueCouponId)
        }

        //8. 주문 상태 결제 완료로 변경
        orderService.completePayment(order.id)

        //9. 결제 정보 저장
        val payment = paymentService.pay(Payment(order.id, order.totalPrice))

        return OrderCompletedResult.from(order, payment)
    }
}
