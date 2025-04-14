package kr.hhplus.be.server.interfaces.coupon

import kr.hhplus.be.server.application.coupon.CouponIssueResult
import kr.hhplus.be.server.application.coupon.CouponQueryService
import kr.hhplus.be.server.application.coupon.FirstComeCouponIssueService
import kr.hhplus.be.server.application.coupon.MyCouponResult
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest(CouponController::class)
class CouponControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var couponQueryService: CouponQueryService

    @MockitoBean
    private lateinit var firstComeCouponIssueService: FirstComeCouponIssueService

    @DisplayName("쿠폰을 발급하면, 발급한 쿠폰의 정보를 반환한다")
    @Test
    fun issueCoupon() {
        val userId = 1L
        val couponId = 5L
        val result = CouponIssueResult(1L, userId, couponId)

        `when`(firstComeCouponIssueService.issuedCoupon(userId, couponId)).thenReturn(result)

        mockMvc.post("/api/v1/coupons/$couponId/issue?userId=$userId") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            jsonPath("$.id") { value(result.id.toInt()) }
            jsonPath("$.userId") { value(result.userId.toInt()) }
            jsonPath("$.couponId") { value(result.couponId.toInt()) }
        }

        verify(firstComeCouponIssueService).issuedCoupon(userId, couponId)
    }

    @DisplayName("이미 발급된 쿠폰을 발급하면 500 error 반환한다")
    @Test
    fun duplicatedCoupon() {
        val userId = 1L
        val couponId = 5L

        `when`(firstComeCouponIssueService.issuedCoupon(userId, couponId))
            .thenThrow(IllegalStateException("이미 발급된 쿠폰입니다"))

        mockMvc.post("/api/v1/coupons/$couponId/issue?userId=$userId") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isInternalServerError() }
            jsonPath("$.code") { value(500) }
            jsonPath("$.message") { value("이미 발급된 쿠폰입니다") }
        }

        verify(firstComeCouponIssueService).issuedCoupon(userId, couponId)
    }

    @DisplayName("쿠폰의 발급 시, 쿠폰의 재고가 부족하면 500 error 반환한다")
    @Test
    fun insufficientCoupon() {
        val userId = 1L
        val couponId = 5L

        `when`(firstComeCouponIssueService.issuedCoupon(userId, couponId))
            .thenThrow(IllegalStateException("쿠폰의 재고가 부족합니다"))

        mockMvc.post("/api/v1/coupons/$couponId/issue?userId=$userId") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isInternalServerError() }
            jsonPath("$.code") { value(500) }
            jsonPath("$.message") { value("쿠폰의 재고가 부족합니다") }
        }

        verify(firstComeCouponIssueService).issuedCoupon(userId, couponId)
    }

    @DisplayName("내가 보유한 쿠폰 조회 요청 시, 보유한 쿠폰 정보를 반환한다")
    @Test
    fun findMyCoupon() {
        val userId = 1L
        val couponId = 5L
        val result = listOf(MyCouponResult(1L, userId, couponId, "ACTIVE"))

        `when`(couponQueryService.findMyCoupons(userId)).thenReturn(result)

        mockMvc.get("/api/v1/coupons?userId=$userId") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$[0].id") { value(result.first().id.toInt()) }
            jsonPath("$[0].userId") { value(result.first().userId.toInt()) }
            jsonPath("$[0].couponId") { value(result.first().couponId.toInt()) }
            jsonPath("$[0].status") { value(result.first().status) }
        }

        verify(couponQueryService).findMyCoupons(userId)
    }
}