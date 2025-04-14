package kr.hhplus.be.server.domain.order

enum class OrderStatus(description: String) {
    CREATED("주문생성"),
    PAID("결제완료")
}