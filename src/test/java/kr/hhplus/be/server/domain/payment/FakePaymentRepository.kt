package kr.hhplus.be.server.domain.payment

import kr.hhplus.be.server.domain.payment.Payment
import kr.hhplus.be.server.domain.payment.PaymentRepository
import java.util.concurrent.ConcurrentHashMap

class FakePaymentRepository : PaymentRepository {
    private val payments = ConcurrentHashMap<Long, Payment>()

    override fun save(payment: Payment): Payment {
        payments.put(payment.id, payment)
        return payment
    }

    override fun findById(id: Long): Payment? {
        return payments.get(id)
    }
}
