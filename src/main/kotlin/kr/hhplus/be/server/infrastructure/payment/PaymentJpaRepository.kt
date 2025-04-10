package kr.hhplus.be.server.infrastructure.payment

import kr.hhplus.be.server.application.payment.Payment
import kr.hhplus.be.server.application.payment.PaymentRepository
import org.springframework.stereotype.Repository

@Repository
class PaymentJpaRepository : PaymentRepository {
    override fun save(payment: Payment) : Payment {
        TODO("Not yet implemented")
    }
}