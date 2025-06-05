package kr.hhplus.be.server.infrastructure.coupon

import kr.hhplus.be.server.domain.coupon.AvailableCouponsRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class AvailableCouponsRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, String>
) : AvailableCouponsRepository {

    companion object {
        const val KEY = "available_coupons"
    }

    override fun contains(couponId: Long): Boolean {
        return redisTemplate.opsForSet()
            .isMember(KEY, couponId) == true
    }

    override fun isUnavailable(couponId: Long): Boolean {
        return !contains(couponId)
    }

    override fun add(couponId: Long) {
        redisTemplate.opsForSet()
            .add(KEY, couponId.toString())
    }

    override fun remove(couponId: Long) {
        redisTemplate.opsForSet()
            .remove(KEY, couponId.toString())
    }
}
