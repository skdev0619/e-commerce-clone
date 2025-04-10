package kr.hhplus.be.server.domain.order

import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class UserCashValidator {
    fun validate(cash: OrderCashSnapShot, totalPrice: BigDecimal) {
        if (cash.balance < totalPrice) {
            throw IllegalStateException("잔액이 부족합니다")
        }
    }
}
