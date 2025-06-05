package kr.hhplus.be.server.domain.order

class OrderEvent {
    data class Completed(
        val id: String,
        val orderInfo: OrderInfo
    )
}