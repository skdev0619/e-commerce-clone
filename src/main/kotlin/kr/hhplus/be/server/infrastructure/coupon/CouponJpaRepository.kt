package kr.hhplus.be.server.infrastructure.coupon

import kr.hhplus.be.server.coupon.domain.Coupon
import kr.hhplus.be.server.domain.coupon.CouponRepository
import org.springframework.stereotype.Repository

@Repository
class CouponJpaRepository : CouponRepository {
    override fun save(coupon: Coupon): Coupon {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): Coupon? {
        TODO("Not yet implemented")
    }
}