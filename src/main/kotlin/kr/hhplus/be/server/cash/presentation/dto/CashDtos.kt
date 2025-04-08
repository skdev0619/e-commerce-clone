package kr.hhplus.be.server.cash.presentation.dto

data class CashChargeResponse(
    val id: Long,
    val chargeAmount: Long,
    val newAmount: Long
)

data class CashDto(
    val id: Long,
    val amount: Long
)