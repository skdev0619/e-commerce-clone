package kr.hhplus.be.server.interfaces.order

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity

@Tag(name = "Order", description = "주문 관련 API")
interface OrderApiSpecification {

    @Operation(summary = "주문 생성", description = "사용자 id와 주문 내역을 입력받아 주문 생성한다")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "주문이 성공적으로 생성됨",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = OrderCreateResponse::class),
                    examples = [ExampleObject(
                        name = "성공 응답 예시",
                        value = """
                        {
                            "id": 1,
                            "userId" : 5,
                            "status" : CREATED,
                            "issueCouponId" : 5,
                            "orderDateTime" : 2025-04-11 04:40:05,
                            "items": {[
                                { "productId": 1, "quantity": 1, "price": 10000 },
                                { "productId": 2, "quantity": 2, "price": 30000 }
                            ]},
                            "totalPrice": 70000,
                            "paymentId": 2
                        }
                        """
                    )]
                )]
            ),
            ApiResponse(
                responseCode = "409",
                description = "재고 부족으로 주문 실패",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            example = """
                                {
                                    "error": "OutOfStock",
                                    "message": "상품의 재고가 부족합니다.",
                                    "status": 409
                                }
                            """
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "402",
                description = "잔액 부족으로 주문 실패",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            example = """
                                {
                                    "error": "InsufficientFunds",
                                    "message": "잔액이 부족합니다.",
                                    "status": 402
                                }
                            """
                        )
                    )
                ]
            )
        ]
    )
    fun createOrder(
        @RequestBody(
            description = "주문 생성 request",
            required = true,
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = OrderCreateRequest::class),
                examples = [ExampleObject(
                    name = "요청 예시",
                    value = """
                    {
                        "userId": 7,
                        "items": [
                            { "productId": 1, "quantity": 1, "price" : 10000 },
                            { "productId": 2, "quantity": 2, "price" : 20000 }
                        ],
                        "couponId": "11"
                    }
                    """
                )]
            )]
        ) request: OrderCreateRequest
    ): ResponseEntity<OrderCreateResponse>
}