package kr.hhplus.be.server.domain.coupon

class FakeAvailableCouponsRepository : AvailableCouponsRepository {

    private val couponIds = HashSet<Long>()

    override fun contains(couponId: Long): Boolean {
        return couponId in couponIds
    }

    override fun isUnavailable(couponId: Long): Boolean {
        return !contains(couponId)
    }

    override fun add(couponId: Long) {
        couponIds.add(couponId)
    }

    override fun remove(couponId: Long) {
        couponIds.remove(couponId)
    }
}