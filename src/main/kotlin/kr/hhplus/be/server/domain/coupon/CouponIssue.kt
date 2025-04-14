package kr.hhplus.be.server.domain.coupon

import kr.hhplus.be.server.domain.common.AuditInfo

class CouponIssue(
    val id: Long,
    val userId: Long,
    val couponId: Long,
    var status: CouponStatus,
    val auditInfo: AuditInfo = AuditInfo()
) {
    constructor(userId: Long, couponId: Long, status: CouponStatus)
            : this(0L, userId, couponId, status)

    fun use() {
        if (status != CouponStatus.ACTIVE) {
            throw IllegalStateException("쿠폰의 직전 상태는 사용 가능이어야 합니다.")
        }
        status = CouponStatus.USED
        auditInfo.update()
    }

    fun expired() {
        if (status != CouponStatus.ACTIVE) {
            throw IllegalStateException("쿠폰의 직전 상태는 사용 가능이어야 합니다.")
        }
        status = CouponStatus.EXPIRED
        auditInfo.update()
    }

    fun isActive(): Boolean {
        return status == CouponStatus.ACTIVE
    }
}