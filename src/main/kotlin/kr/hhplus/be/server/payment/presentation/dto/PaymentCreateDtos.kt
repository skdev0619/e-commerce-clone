package kr.hhplus.be.server.payment.presentation.dto

import java.time.LocalDateTime

data class PaymentCreateResponse(
    val id: Long,
    val orderId: Long,
    val amount: Int,
    val paymentTime: LocalDateTime
)