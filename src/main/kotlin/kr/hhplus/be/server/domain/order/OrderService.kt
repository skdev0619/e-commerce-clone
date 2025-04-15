package kr.hhplus.be.server.domain.order

import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository
) {
    fun create(command: OrderCommand): Order {
        val order = Order.create(
            userId = command.userId,
            issueCouponId = command.couponIssueId,
            orderItems = command.orderItems
        )

        order.applyDiscount(command.discountStrategy)

        return orderRepository.save(order)
    }

    fun completePayment(orderId: Long): Order {
        val order = orderRepository.findById(orderId)
            ?: throw NoSuchElementException("주문이 존재하지 않습니다")

        order.completePayment()
        return order
    }
}
