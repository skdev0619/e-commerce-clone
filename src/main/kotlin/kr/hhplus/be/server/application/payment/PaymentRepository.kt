package kr.hhplus.be.server.application.payment

interface PaymentRepository {
    fun save(payment: Payment) : Payment
}
