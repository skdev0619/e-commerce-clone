package kr.hhplus.be.server.application.order.internal

import kr.hhplus.be.server.domain.cash.UserCashService
import kr.hhplus.be.server.domain.order.OrderCashSnapShot
import org.springframework.stereotype.Component

/*
* 주문 생성 당시의 사용자의 잔액 조회
* */

@Component
class OrderCashSnapShotReader(
    private val userCashService: UserCashService
) {
    fun read(userId: Long): OrderCashSnapShot {
        val userCash = userCashService.findByUserId(userId)
        return OrderCashSnapShot(userCash.userId, userCash.balance)
    }
}
