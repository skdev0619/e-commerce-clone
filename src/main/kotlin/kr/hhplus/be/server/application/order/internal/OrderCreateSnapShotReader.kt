package kr.hhplus.be.server.application.order.internal

import kr.hhplus.be.server.application.order.OrderCreateCommand
import kr.hhplus.be.server.domain.order.OrderCreateSnapShot
import org.springframework.stereotype.Component

@Component
class OrderCreateSnapShotReader(
    private val productReader: OrderProductSnapShotReader,
    private val couponReader: OrderCouponSnapShotReader,
    private val cashReader: OrderCashSnapShotReader
) {
    fun createSnapShot(command: OrderCreateCommand): OrderCreateSnapShot {
        val products = productReader.read(command.toProductIds())
        val coupon = command.issueCouponId?.let { couponReader.read(it) }
        val cash = cashReader.read(command.userId)

        return OrderCreateSnapShot(products, coupon, cash)
    }
}
