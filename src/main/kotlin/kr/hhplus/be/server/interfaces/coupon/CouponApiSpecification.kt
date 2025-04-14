package kr.hhplus.be.server.interfaces.coupon

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity

@Tag(name = "Coupon", description = "Coupon 관련 API")
interface CouponApiSpecification {

    @Operation(summary = "쿠폰 발급", description = "사용자에게 쿠폰을 발급한다")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "쿠폰이 성공적으로 발급됨",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = IssueCouponResponse::class),
                    examples = [ExampleObject(value = "{\"id\": 11, \"name\": \"1000원 할인\", \"discountType\": \"FIXED_AMOUNT\", \"discountValue\": 1000, \"minOrderAmount\": 10000, \"maxDiscountAmount\": 1000, \"expiredDate\": \"2025-12-31T23:59:00\"}")]
                )]
            ),
            ApiResponse(
                responseCode = "409",
                description = "이미 발급된 쿠폰",
                content = [Content(
                    mediaType = "application/json",
                    examples = [ExampleObject(value = "{\"error\": \"Conflict\", \"message\": \"이미 발급된 쿠폰입니다.\", \"status\": 409}")]
                )]
            ),
            ApiResponse(
                responseCode = "422",
                description = "쿠폰의 재고가 없음",
                content = [Content(
                    mediaType = "application/json",
                    examples = [ExampleObject(value = "{\"error\": \"Unprocessable Entity\", \"message\": \"쿠폰 재고가 없습니다.\", \"status\": 422}")]
                )]
            )
        ]
    )
    fun issue(couponId: Long, userId: Long): ResponseEntity<IssueCouponResponse>

    @Operation(summary = "쿠폰 목록 조회", description = "사용자가 보유한 쿠폰목록을 조회한다")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "성공적으로 쿠폰 목록을 반환",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = MyCouponResponse::class),
                    examples = [ExampleObject(value = "[{\"id\": 11, \"name\": \"1000원 할인\", \"discountType\": \"FIXED_AMOUNT\", \"discountValue\": 1000, \"minOrderAmount\": 10000, \"maxDiscountAmount\": 1000, \"status\": \"ACTIVE\", \"expiredDate\": \"2025-12-31T23:59:00\"}]")]
                )]
            )
        ]
    )
    fun coupons(userId: Long): ResponseEntity<List<MyCouponResponse>>
}