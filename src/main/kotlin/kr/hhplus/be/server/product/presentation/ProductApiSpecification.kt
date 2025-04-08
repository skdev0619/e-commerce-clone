package kr.hhplus.be.server.product.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import kr.hhplus.be.server.product.presentation.dto.ProductDto
import org.springframework.http.ResponseEntity

@Tag(name = "Product", description = "상품 관련 API")
interface ProductApiSpecification {
    
    @Operation(summary = "상품 정보 조회", description = "상품의 id로 상품의 정보를 조회한다")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "성공적으로 상품 정보를 반환",
                content = [
                    Content(
                        mediaType = "application/json",
                        examples = [
                            ExampleObject(
                                name = "정상 응답 예시",
                                summary = "상품 정보 조회 성공",
                                value = """
                                {
                                    "id": 1,
                                    "name": "검정 버킷햇",
                                    "stock": 100
                                }
                                """
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "잘못된 요청 (상품 ID가 유효하지 않음)",
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
                                    "message": "상품 ID는 1 이상의 정수여야 합니다.",
                                    "status": 400
                                }
                                """
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "상품을 찾을 수 없음",
                content = [
                    Content(
                        mediaType = "application/json",
                        examples = [
                            ExampleObject(
                                name = "상품 없음 예시",
                                summary = "존재하지 않는 상품 ID 요청",
                                value = """
                                {
                                    "error": "Not Found",
                                    "message": "해당 ID의 상품을 찾을 수 없습니다.",
                                    "status": 404
                                }
                                """
                            )
                        ]
                    )
                ]
            )
        ]
    )
    fun product(@Parameter(description = "조회할 상품 ID", example = "1") id: Long): ResponseEntity<ProductDto>
}
