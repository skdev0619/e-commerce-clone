package kr.hhplus.be.server.domain.order

import java.math.BigDecimal

//주문 생성 시, 상품 정보
data class OrderProductSnapShot(
    val productId: Long,
    val price: BigDecimal,
    val stock: Int
)
//주문 생성 시, 쿠폰 정보
data class OrderCouponSnapShot(
    val couponIssueId: Long,
    val discountType: OrderDiscountType,
    val discountValue: Int
)
//주문 생성 시, 잔액 정보
data class OrderCashSnapShot(
    val userId: Long,
    val balance: BigDecimal
)

data class OrderCreateSnapShot(
    val products: List<OrderProductSnapShot>,
    val coupon: OrderCouponSnapShot?,
    val cash: OrderCashSnapShot
)