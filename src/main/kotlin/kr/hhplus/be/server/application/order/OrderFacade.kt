package kr.hhplus.be.server.application.order

import kr.hhplus.be.server.domain.cash.UserCashService
import kr.hhplus.be.server.domain.coupon.CouponService
import kr.hhplus.be.server.domain.order.OrderEvent
import kr.hhplus.be.server.domain.order.OrderEventPublisher
import kr.hhplus.be.server.domain.order.OrderService
import kr.hhplus.be.server.domain.payment.Payment
import kr.hhplus.be.server.domain.payment.PaymentService
import kr.hhplus.be.server.domain.product.ProductService
import org.springframework.orm.ObjectOptimisticLockingFailureException
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
    private val paymentService: PaymentService,
    private val eventPublisher: OrderEventPublisher
) {
    fun createOrder(criteria: OrderCriteria): OrderCompletedResult {
        //1. 유효한 쿠폰 조회
        val coupon = criteria.issueCouponId?.let {
            couponService.findActiveCouponByIssueId(it)
        }

        //2. 할인 전락 구현체 조회
        val discountStrategy = discountStrategyFactory.create(coupon)

        //3. 주문 생성
        val order = orderService.create(criteria.toOrderCommand(discountStrategy))

        //4. 금액 차감
        try {
            userCashService.use(criteria.userId, order.totalPrice)
        } catch (e: ObjectOptimisticLockingFailureException) {
            throw RuntimeException("동시에 차감 요청이 처리되어 실패했습니다. 잠시 후 다시 시도해주세요.", e)
        }

        //5. 쿠폰 있으면 쿠폰 사용
        coupon?.let {
            couponService.use(criteria.issueCouponId)
        }

        //6. 주문 상태 결제 완료로 변경
        orderService.completePayment(order.id)

        //7. 결제 정보 저장
        val payment = paymentService.pay(Payment(order.id, order.totalPrice))

        //8. 재고 감소
        //비관적 락 사용으로 인한 실행 순서 변경
        productService.decreaseStock(criteria.toProductQuantities())

        //9. 주문 완료 이벤트 발행
        eventPublisher.publish(OrderEvent.Completed(criteria.toOrderInfo()))

        return OrderCompletedResult.from(order, payment)
    }
}
