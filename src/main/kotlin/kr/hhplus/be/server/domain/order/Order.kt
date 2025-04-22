package kr.hhplus.be.server.domain.order

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseRootEntity
import kr.hhplus.be.server.domain.order.discount.DiscountStrategy
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false)
    val userId: Long,

    @Enumerated(EnumType.STRING)
    var status: OrderStatus,

    @Column(nullable = true)
    val couponIssueId: Long?,

    @Column(nullable = true)
    var paidDate: LocalDateTime? = null,

    @Embedded
    val orderItems: OrderItems,

    @Column(nullable = false)
    var totalPrice: BigDecimal,

    ) : BaseRootEntity<Order>() {

    companion object {
        fun create(userId: Long, couponIssueId: Long?, orderItems: OrderItems): Order {
            val totalPrice = orderItems.totalPrice()
            return Order(
                userId = userId,
                status = OrderStatus.CREATED,
                couponIssueId = couponIssueId,
                orderItems = orderItems,
                totalPrice = totalPrice
            )
        }
    }

    fun applyDiscount(discountStrategy: DiscountStrategy) {
        val discountedPrice = orderItems.calculateDiscountPrice(discountStrategy)
        totalPrice = discountedPrice
    }

    fun completePayment() {
        if (status != OrderStatus.CREATED) {
            throw IllegalStateException("주문의 상태가 이상합니다")
        }
        status = OrderStatus.PAID
        paidDate = LocalDateTime.now()
    }
}
