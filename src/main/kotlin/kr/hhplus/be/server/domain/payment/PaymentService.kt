package kr.hhplus.be.server.domain.payment

import org.springframework.stereotype.Service

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository
) {
    fun pay(payment: Payment): Payment {
        return paymentRepository.save(payment)
    }
}
