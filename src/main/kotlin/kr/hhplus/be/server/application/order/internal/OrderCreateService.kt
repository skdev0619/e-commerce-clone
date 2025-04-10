package kr.hhplus.be.server.application.order.internal

import kr.hhplus.be.server.application.order.OrderCreateCommand
import kr.hhplus.be.server.domain.order.*
import org.springframework.stereotype.Service

@Service
class OrderCreateService(
    private val orderSnapShotReader: OrderCreateSnapShotReader,
    private val productValidator: OrderProductValidator,
    private val priceCalculator: OrderTotalPriceCalculator,
    private val cashValidator: UserCashValidator,
    private val orderRepository: OrderRepository
) {
    fun createOrder(command: OrderCreateCommand): OrderCreateResult {
        val orderItems = command.toOrderItems()

        //1. 주문 생성 당시의 상품, 잔액, 쿠폰 정보 조회
        val snapShot = orderSnapShotReader.createSnapShot(command)

        //2. 상품 검증
        productValidator.validate(snapShot.products, orderItems)

        //3. 주문 금액 계산
        val totalPrice = priceCalculator.calculateTotalPrice(orderItems, snapShot.coupon)

        //4. 잔액 검증
        cashValidator.validate(snapShot.cash, totalPrice)

        //5. 주문 저장
        val createdOrder = orderRepository.save(
            Order(
                command.userId,
                OrderStatus.CREATED,
                snapShot.coupon?.couponIssueId,
                orderItems,
                totalPrice
            )
        )
        return OrderCreateResult.from(createdOrder)
    }
}
