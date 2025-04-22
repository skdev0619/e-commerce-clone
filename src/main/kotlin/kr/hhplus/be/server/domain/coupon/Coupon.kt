package kr.hhplus.be.server.coupon.domain

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity
import kr.hhplus.be.server.domain.coupon.CouponIssue
import kr.hhplus.be.server.domain.coupon.CouponStatus
import kr.hhplus.be.server.domain.coupon.DiscountType

@Entity
class Coupon(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false)
    val name: String,

    @Enumerated(EnumType.STRING)
    val discountType: DiscountType,

    @Column(nullable = false)
    val discountValue: Int,

    @Column(nullable = false)
    var stock: Int

) : BaseEntity() {
    constructor(name: String, discountType: DiscountType, discountValue: Int, stock: Int)
            : this(0L, name, discountType, discountValue, stock)

    fun issue(userId: Long): CouponIssue {
        check(stock > 0) { "쿠폰의 재고가 부족합니다" }
        stock--

        return CouponIssue(
            userId = userId,
            couponId = id,
            status = CouponStatus.ACTIVE
        )
    }
}
