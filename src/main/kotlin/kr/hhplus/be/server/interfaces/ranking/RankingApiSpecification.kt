package kr.hhplus.be.server.interfaces.ranking

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject

import org.springframework.http.ResponseEntity

@Tag(name = "Ranking", description = "랭킹 관련 API")
interface RankingApiSpecification {

    @Operation(
        summary = "베스트셀러 상품 N개 조회",
        description = "특정 기간의 인기상품 상위 N개(기본 값 5개) 상품을 조회한다",
        parameters = [
            Parameter(
                name = "startDate",
                description = "조회 시작일",
                example = "2025-04-24",
                `in` = ParameterIn.QUERY,
                schema = Schema(type = "string", format = "date")
            ),
            Parameter(
                name = "endDate",
                description = "조회 종료일",
                example = "2025-04-26",
                `in` = ParameterIn.QUERY,
                schema = Schema(type = "string", format = "date")
            ),
            Parameter(
                name = "limit",
                description = "조회할 상품의 수",
                example = "5",
                `in` = ParameterIn.QUERY,
                schema = Schema(type = "integer", defaultValue = "5")
            )
        ]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "성공적으로 인기 상품 TOP 5개를 조회",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = BestSellingProductsResponse::class),
                        examples = [
                            ExampleObject(
                                name = "성공 응답 예시",
                                value = """
                                [   
                                        { "productId": 1, "name": "베이직무지볼캡", "salesCount": 100, "price": 13900 },
                                        { "productId": 2, "name": "검정 버킷햇", "salesCount": 90, "price": 20000 },
                                        { "productId": 3, "name": "숏챙 페도라", "salesCount": 80, "price": 18900 },
                                        { "productId": 4, "name": "NYC 비니", "salesCount": 70, "price": 12900 },
                                        { "productId": 5, "name": "털방울 비니", "salesCount": 60, "price": 19900 }
                                    
                                ]
                                """
                            )
                        ]
                    )
                ]
            )
        ]
    )
    fun topSellingProducts(@ParameterObject request: BestSellingProductsRequest): ResponseEntity<List<BestSellingProductsResponse>>
}