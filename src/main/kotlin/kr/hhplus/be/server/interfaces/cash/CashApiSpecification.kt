package kr.hhplus.be.server.interfaces.cash

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity

@Tag(name = "Cash", description = "사용자의 캐시 조회 및 충전 API")
interface CashApiSpecification {

    @Operation(summary = "캐시 조회", description = "사용자의 id로 캐시 잔액을 조회한다")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "성공적으로 캐시 정보를 반환",
                content = [
                    Content(
                        mediaType = "application/json",
                        examples = [
                            ExampleObject(
                                name = "정상 응답 예시",
                                summary = "캐시 조회 성공",
                                value = """
                                {
                                    "id": 1,
                                    "userId": 101,
                                    "balance": 10000
                                }
                                """
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "사용자 정보 없음",
                content = [
                    Content(
                        mediaType = "application/json",
                        examples = [
                            ExampleObject(
                                name = "사용자 없음 예시",
                                summary = "존재하지 않는 사용자 ID 요청",
                                value = """
                                {
                                    "error": "Not Found",
                                    "message": "해당 사용자의 잔액이 존재하지 않습니다",
                                    "status": 404
                                }
                                """
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "잘못된 요청 (사용자 ID가 유효하지 않음)",
                content = [
                    Content(
                        mediaType = "application/json",
                        examples = [
                            ExampleObject(
                                name = "잘못된 요청 예시",
                                summary = "음수 또는 유효하지 않은 ID 요청",
                                value = """
                                {
                                    "error": "Bad Request",
                                    "message": "사용자 id는 1 이상이어야 합니다",
                                    "status": 400
                                }
                                """
                            )
                        ]
                    )
                ]
            )
        ]
    )
    fun point(@Parameter(description = "조회할 사용자 ID", example = "101") id: Long): ResponseEntity<CashViewResponse>

    @Operation(summary = "캐시 충전", description = "특정 사용자의 캐시 잔액을 충전한다")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "성공적으로 캐시를 충전",
                content = [
                    Content(
                        mediaType = "application/json",
                        examples = [
                            ExampleObject(
                                name = "충전 성공 예시",
                                summary = "캐시 충전 성공",
                                value = """
                                {
                                    "id": 1,
                                    "userId": 11,
                                    "amount" : 5000,
                                    "finalBalance": 15000
                                }
                                """
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "사용자 정보 없음",
                content = [
                    Content(
                        mediaType = "application/json",
                        examples = [
                            ExampleObject(
                                name = "사용자 없음 예시",
                                summary = "존재하지 않는 사용자 ID 요청",
                                value = """
                                {
                                    "error": "Not Found",
                                    "message": "해당 사용자의 잔액이 존재하지 않습니다",
                                    "status": 404
                                }
                                """
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "잘못된 요청 (사용자 ID가 유효하지 않음)",
                content = [
                    Content(
                        mediaType = "application/json",
                        examples = [
                            ExampleObject(
                                name = "잘못된 요청 예시",
                                summary = "음수 또는 유효하지 않은 ID 요청",
                                value = """
                                {
                                    "error": "Bad Request",
                                    "message": "사용자 id는 1 이상이어야 합니다",
                                    "status": 400
                                }
                                """
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "잘못된 요청 (음수 금액 또는 유효하지 않은 입력값)",
                content = [
                    Content(
                        mediaType = "application/json",
                        examples = [
                            ExampleObject(
                                name = "잘못된 요청 예시",
                                summary = "음수 금액 충전 요청",
                                value = """
                                {
                                    "error": "Bad Request",
                                    "message": "충전할 잔액은 0보다 커야 합니다.",
                                    "status": 400
                                }
                                """
                            )
                        ]
                    )
                ]
            )
        ]
    )
    fun charge(
        @Parameter(description = "충전할 사용자 ID", example = "11") id: Long,
        @RequestBody(
            description = "충전할 금액",
            required = true,
            content = [Content(
                mediaType = "application/json",
                schema = Schema(type = "integer", format = "long"),
                examples = [ExampleObject(
                    name = "충전 요청 예시",
                    value = "10000"
                )]
            )]
        ) amount: Long
    ): ResponseEntity<CashChargeResponse>
}