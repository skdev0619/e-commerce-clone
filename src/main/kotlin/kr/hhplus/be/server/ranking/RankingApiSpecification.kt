package kr.hhplus.be.server.ranking

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import kr.hhplus.be.server.ranking.dto.TopSellingProductResponse
import org.springframework.http.ResponseEntity

@Tag(name = "Ranking", description = "랭킹 관련 API")
interface RankingApiSpecification {

    @Operation(summary = "인기상품 top 5 조회", description = "최근 3일간의 인기상품 상위 5개 상품을 조회한다")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "성공적으로 인기 상품 TOP 5개를 조회",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = TopSellingProductResponse::class),
                        examples = [
                            ExampleObject(
                                name = "성공 응답 예시",
                                value = """
                                {
                                    "products": [
                                        { "id": 1, "name": "베이직무지볼캡", "sales": 100, "price": 13900 },
                                        { "id": 2, "name": "검정 버킷햇", "sales": 90, "price": 20000 },
                                        { "id": 3, "name": "숏챙 페도라", "sales": 80, "price": 18900 },
                                        { "id": 4, "name": "NYC 비니", "sales": 70, "price": 12900 },
                                        { "id": 5, "name": "털방울 비니", "sales": 60, "price": 19900 }
                                    ]
                                }
                                """
                            )
                        ]
                    )
                ]
            )
        ]
    )
    fun topSellingProducts(): ResponseEntity<TopSellingProductResponse>
}