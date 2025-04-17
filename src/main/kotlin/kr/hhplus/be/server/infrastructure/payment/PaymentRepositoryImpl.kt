package kr.hhplus.be.server.infrastructure.payment

import kr.hhplus.be.server.domain.payment.Payment
import kr.hhplus.be.server.domain.payment.PaymentRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class PaymentRepositoryImpl(
    private val jpaRepository: PaymentJpaRepository
) : PaymentRepository {

    override fun save(payment: Payment): Payment {
        return jpaRepository.save(payment)
    }

    override fun findById(id: Long): Payment? {
        return jpaRepository.findByIdOrNull(id)
    }
}
