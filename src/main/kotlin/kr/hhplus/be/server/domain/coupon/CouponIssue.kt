package kr.hhplus.be.server.domain.coupon

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity

@Entity
class CouponIssue(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false)
    val couponId: Long,

    @Enumerated(EnumType.STRING)
    var status: CouponStatus,

    ) : BaseEntity() {

    constructor(userId: Long, couponId: Long, status: CouponStatus)
            : this(0L, userId, couponId, status)

    fun use() {
        if (status != CouponStatus.ACTIVE) {
            throw IllegalStateException("쿠폰의 직전 상태는 사용 가능이어야 합니다.")
        }
        status = CouponStatus.USED
    }

    fun expired() {
        if (status != CouponStatus.ACTIVE) {
            throw IllegalStateException("쿠폰의 직전 상태는 사용 가능이어야 합니다.")
        }
        status = CouponStatus.EXPIRED
    }

    fun isCouponValid(): Boolean {
        return status == CouponStatus.ACTIVE
    }
}