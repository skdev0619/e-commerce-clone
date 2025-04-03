package kr.hhplus.be.server.payment.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import kr.hhplus.be.server.payment.presentation.dto.PaymentCreateResponse
import org.springframework.http.ResponseEntity

@Tag(name = "Payment", description = "결제 관련 API")
interface PaymentApiSpecification {

    @Operation(summary = "결제 생성", description = "주문 ID를 기반으로 생성된 주문 내역을 참고하여 잔액을 결제한다")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "결제 성공",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = PaymentCreateResponse::class),
                    examples = [ExampleObject(
                        name = "결제 성공 예시",
                        value = """
                        {
                            "id": 1,
                            "orderId": 5,
                            "amount": 45000,
                            "createdAt": "2025-04-03T12:00:00"
                        }
                        """
                    )]
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "잘못된 요청 (주문 ID 없음)",
                content = [Content(
                    mediaType = "application/json",
                    examples = [ExampleObject(
                        name = "잘못된 요청 예시",
                        value = """
                        {
                            "error": "InvalidRequest",
                            "message": "주문 ID는 필수입니다."
                        }
                        """
                    )]
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "주문을 찾을 수 없음",
                content = [Content(
                    mediaType = "application/json",
                    examples = [ExampleObject(
                        name = "주문 없음 예시",
                        value = """
                        {
                            "error": "OrderNotFound",
                            "message": "해당 주문을 찾을 수 없습니다."
                        }
                        """
                    )]
                )]
            ),
            ApiResponse(
                responseCode = "402",
                description = "잔액 부족으로 결제 실패",
                content = [Content(
                    mediaType = "application/json",
                    examples = [ExampleObject(
                        name = "잔액 부족 예시",
                        value = """
                        {
                            "error": "InsufficientBalance",
                            "message": "결제를 진행할 수 있는 잔액이 부족합니다."
                        }
                        """
                    )]
                )]
            )
        ]
    )
    fun createPayment(
        @RequestBody(
            description = "결제할 주문 ID",
            required = true,
            content = [Content(
                mediaType = "application/json",
                schema = Schema(type = "long", format = "long"),
                examples = [ExampleObject(
                    name = "결제 요청 예시",
                    value = "5"
                )]
            )]
        ) orderId: Long
    ): ResponseEntity<PaymentCreateResponse>
}