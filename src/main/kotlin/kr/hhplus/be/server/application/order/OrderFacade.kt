package kr.hhplus.be.server.application.order

import kr.hhplus.be.server.application.order.internal.OrderCreateService
import kr.hhplus.be.server.application.order.internal.OrderPaymentService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class OrderFacade(
    private val orderCreateService: OrderCreateService,
    private val orderPaymentService: OrderPaymentService
) {
    fun createOrder(command: OrderCreateCommand): OrderCompletedResult {
        val order = orderCreateService.createOrder(command)
        val payment = orderPaymentService.pay(order.userId, order.id)

        return OrderCompletedResult.from(order, payment)
    }
}
