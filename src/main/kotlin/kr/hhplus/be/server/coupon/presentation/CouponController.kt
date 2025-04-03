package kr.hhplus.be.server.coupon.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import kr.hhplus.be.server.coupon.domain.CouponStatus
import kr.hhplus.be.server.coupon.domain.DiscountType
import kr.hhplus.be.server.coupon.presentation.dto.CouponIssueDto
import kr.hhplus.be.server.coupon.presentation.dto.IssuedCouponResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/coupons")
class CouponController : CouponApiSpecification {

    @PostMapping("/issue")
    override fun issue(): ResponseEntity<IssuedCouponResponse> {
        val response = IssuedCouponResponse(
            5L,
            "30%할인",
            DiscountType.PERCENTAGE,
            30,
            3_000,
            10_000,
            LocalDateTime.of(2025, 12, 31, 23, 59)
        )
        return ResponseEntity.created(URI.create("/api/vi/coupons/issue")).body(response);
    }

    @GetMapping("/coupons")
    override fun coupons(): ResponseEntity<List<CouponIssueDto>> {
        val response = listOf(
            CouponIssueDto(
                1L,
                "1000원 할인",
                DiscountType.FIXED_AMOUNT,
                1_000,
                1_000,
                10_000,
                CouponStatus.ACTIVE,
                LocalDateTime.of(2025, 12, 31, 23, 59)
            ),
            CouponIssueDto(
                2L,
                "10% 할인",
                DiscountType.PERCENTAGE,
                1_000,
                10_000,
                100,
                CouponStatus.EXPIRED,
                LocalDateTime.of(2025, 12, 31, 23, 59)
            )
        )
        return ResponseEntity.ok(response)
    }
}
