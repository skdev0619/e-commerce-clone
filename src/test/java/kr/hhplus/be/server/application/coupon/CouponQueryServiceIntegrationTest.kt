package kr.hhplus.be.server.application.coupon

import kr.hhplus.be.server.domain.coupon.CouponIssue
import kr.hhplus.be.server.domain.coupon.CouponIssueRepository
import kr.hhplus.be.server.domain.coupon.CouponStatus
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class CouponQueryServiceIntegrationTest {
    @Autowired
    private lateinit var couponIssueRepository: CouponIssueRepository

    @Autowired
    private lateinit var couponQueryService: CouponQueryService

    @DisplayName("특정 사용자가 보유한 쿠폰을 조회한다")
    @Test
    fun findMyCoupons() {
        val userId = 17L
        couponIssueRepository.save(CouponIssue(userId = userId, couponId = 3L, CouponStatus.ACTIVE))
        couponIssueRepository.save(CouponIssue(userId = userId, couponId = 5L, CouponStatus.ACTIVE))

        val myCoupons = couponQueryService.findMyCoupons(userId)

        val couponByUserId = couponIssueRepository.findByUserId(userId)
        assertThat(myCoupons).extracting("userId", "couponId", "status")
            .containsExactly(
                Tuple.tuple(couponByUserId[0].userId, couponByUserId[0].couponId, couponByUserId[0].status.name),
                Tuple.tuple(couponByUserId[1].userId, couponByUserId[1].couponId, couponByUserId[1].status.name)
            )
    }
}

