package kr.hhplus.be.server.interfaces.coupon

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
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
                    examples = [ExampleObject(
                        value = """
                        {
                            "id": 5,
                            "userId": 1,
                            "couponId": 1,
                            "name": "10%할인",
                            "discountType": "PERCENTAGE",
                            "discountValue": 10,
                            "status": "ACTIVE"
                        }
                        """
                    )]
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
    fun issue(
        @Parameter(description = "발급받을 쿠폰의 ID", example = "1") couponId: Long,
        @Parameter(description = "사용자 ID", example = "1") userId: Long
    ): ResponseEntity<IssueCouponResponse>

    @Operation(summary = "쿠폰 목록 조회", description = "사용자가 보유한 쿠폰목록을 조회한다")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "성공적으로 쿠폰 목록을 반환",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = MyCouponResponse::class),
                    examples = [
                        ExampleObject(
                            name = "쿠폰 리스트 예시",
                            value = """
                            [
                              {
                                "id": 1,
                                "userId": 2,
                                "couponId": 1,
                                "status": "ACTIVE"
                              },
                              {
                                "id": 2,
                                "userId": 2,
                                "couponId": 2,
                                "status": "USED"
                              },
                              {
                                "id": 3,
                                "userId": 2,
                                "couponId": 3,
                                "status": "EXPIRED"
                              }
                            ]
                            """
                        )
                    ]
                )]
            )
        ]
    )
    fun coupons(
        @Parameter(
            description = "조회할 사용자 ID",
            example = "2"
        ) userId: Long
    ): ResponseEntity<List<MyCouponResponse>>
}