package kr.hhplus.be.server.infrastructure.coupon

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import java.time.LocalDateTime

@SpringBootTest
class CouponRequestRepositoryImplTest {

    @Autowired
    private lateinit var redis: RedisTemplate<String, String>

    private lateinit var repository: CouponRequestRepositoryImpl

    private val userCouponKeyPrefix = CouponRequestRepositoryImpl.COUPON_REQUEST_USER_KEY
    private val couponKey = CouponRequestRepositoryImpl.COUPON_REQUEST_KEY

    @DisplayName("쿠폰 발급 요청을 추가한다")
    @Test
    fun add() {
        //given
        val couponId = 1L
        val couponUserKey = "${userCouponKeyPrefix}:$couponId"
        val repository = CouponRequestRepositoryImpl(redis)

        //when
        repository.add(couponId, userId = 11L, LocalDateTime.now())
        repository.add(couponId, userId = 12L, LocalDateTime.now())

        //then
        val couponIds = redis.opsForSet()
            .members(couponKey)
        assertThat(couponIds).isEqualTo(setOf("1"))

        val userIds = redis.opsForZSet()
            .range(couponUserKey, 0, -1)
        assertThat(userIds).isEqualTo(setOf("11", "12"))
    }

    @DisplayName("발급 요청이 있는 쿠폰 id목록을 조회한다")
    @Test
    fun getCouponIds() {
        val repository = CouponRequestRepositoryImpl(redis)

        repository.add(couponId = 11L, userId = 11L, LocalDateTime.now())
        repository.add(couponId = 21L, userId = 11L, LocalDateTime.now())
        repository.add(couponId = 31L, userId = 11L, LocalDateTime.now())

        val couponIds = repository.getCouponIds()
        assertThat(couponIds).contains(11L, 21L, 31L)
    }

    @DisplayName("쿠폰 발급 요청을 N건 조회한다")
    @Test
    fun getTopRequests() {
        val couponId = 15L
        val repository = CouponRequestRepositoryImpl(redis)

        repository.add(couponId, userId = 1L, LocalDateTime.now())
        repository.add(couponId, userId = 2L, LocalDateTime.now())
        repository.add(couponId, userId = 3L, LocalDateTime.now())

        val couponRequests = repository.getTopRequests(couponId, 2)
        assertThat(couponRequests.userIds).hasSize(2)
        assertThat(couponRequests.userIds).isEqualTo(listOf(1L, 2L))
    }

    @DisplayName("couponId로 쿠폰 발급 요청 관련 데이터를 제거한다")
    @Test
    fun removeByCouponId() {
        //given
        val couponId = 19L
        val couponUserKey = "${userCouponKeyPrefix}:$couponId"
        val repository = CouponRequestRepositoryImpl(redis)
        repository.add(couponId, userId = 1L, LocalDateTime.now())

        //when
        repository.removeByCouponId(couponId)

        //then
        val couponIds = redis.opsForSet()
            .members(couponKey)
        assertThat(couponIds).isEmpty()

        val userIds = redis.opsForZSet()
            .range(couponUserKey, 0, -1)
        assertThat(userIds).isEmpty()
    }
}
