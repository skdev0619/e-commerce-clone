package kr.hhplus.be.server.infrastructure.coupon

import kr.hhplus.be.server.domain.coupon.CouponRequestsRepository
import kr.hhplus.be.server.domain.coupon.CouponRequests
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.time.ZoneOffset

@Repository
class CouponRequestRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, String>
) : CouponRequestsRepository {

    companion object {
        const val COUPON_REQUEST_KEY = "coupon:request"
        const val COUPON_REQUEST_USER_KEY = "coupon:request:user"
    }

    private fun userKey(couponId: Long): String = "$COUPON_REQUEST_USER_KEY:${couponId}"

    override fun add(couponId: Long, userId: Long, requestedAt: LocalDateTime) {
        //쿠폰 id
        redisTemplate.opsForSet()
            .add(COUPON_REQUEST_KEY, couponId.toString())

        //쿠폰 발급 요청 id
        val score = toScore(requestedAt)
        redisTemplate.opsForZSet()
            .add(userKey(couponId), userId.toString(), score)
    }

    private fun toScore(requestedAt: LocalDateTime): Double {
        return requestedAt.toInstant(ZoneOffset.UTC).toEpochMilli().toDouble()
    }

    override fun getCouponIds(): List<Long> {
        return redisTemplate.opsForSet()
            .members(COUPON_REQUEST_KEY)
            ?.mapNotNull { it.toLongOrNull() }
            ?: emptyList()
    }

    override fun getTopRequests(couponId: Long, count: Long): CouponRequests {
        val hasCoupon = redisTemplate.opsForSet()
            .isMember(COUPON_REQUEST_KEY, couponId.toString()) ?: false

        if (hasCoupon) {
            //TODO range > remove를 zpopmin로 변경할 것
            val range = redisTemplate.opsForZSet()
                .range(userKey(couponId), 0, count - 1) ?: emptyList()

            redisTemplate.opsForZSet()
                .remove(userKey(couponId), *range.toTypedArray())

            return CouponRequests(couponId, range.mapNotNull { it.toLongOrNull() })
        }
        throw NoSuchElementException("쿠폰 요청 정보가 존재하지 않습니다")
    }

    override fun removeByCouponId(couponId: Long) {
        redisTemplate.delete(COUPON_REQUEST_KEY)
        redisTemplate.delete(userKey(couponId))
    }
}
