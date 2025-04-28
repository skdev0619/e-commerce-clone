package kr.hhplus.be.server.domain.coupon

import kr.hhplus.be.server.coupon.domain.Coupon
import java.util.concurrent.ConcurrentHashMap

class FakeCouponRepository : CouponRepository {

    private val coupons = ConcurrentHashMap<Long, Coupon>()

    override fun save(coupon: Coupon): Coupon {
        coupons.put(coupon.id, coupon)
        return coupon
    }

    override fun findById(id: Long): Coupon? {
        return coupons.get(id)
    }

    override fun findByIdWithLock(id: Long): Coupon? {
        return findById(id)
    }
}
