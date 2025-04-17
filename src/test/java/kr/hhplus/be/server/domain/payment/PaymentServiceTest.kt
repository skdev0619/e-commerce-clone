package kr.hhplus.be.server.domain.payment

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class PaymentServiceTest {
    private val paymentRepository = FakePaymentRepository()
    private val paymentService = PaymentService(paymentRepository)

    @DisplayName("결제 내역 정보를 저장한다")
    @Test
    fun pay() {
        val orderId = 5L
        val orderPrice = BigDecimal(20_000)

        val payment = paymentService.pay(Payment(orderId, orderPrice))

        assertThat(payment)
            .extracting("orderId", "amount")
            .containsExactly(orderId, orderPrice)
    }
}
