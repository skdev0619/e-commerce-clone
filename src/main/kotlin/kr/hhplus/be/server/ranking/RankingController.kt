package kr.hhplus.be.server.ranking

import kr.hhplus.be.server.ranking.dto.TopSellingProductDto
import kr.hhplus.be.server.ranking.dto.TopSellingProductResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/ranking")
class RankingController : RankingApiSpecification {

    @GetMapping("/top-selling-products")
    override fun topSellingProducts(): ResponseEntity<TopSellingProductResponse> {
        val products = listOf(
            TopSellingProductDto(1L, "베이직무지볼캡", 100, 13_900),
            TopSellingProductDto(2L, "검정 버킷햇", 90, 20_000),
            TopSellingProductDto(3L, "숏챙 페도라", 80, 18_900),
            TopSellingProductDto(4L, "NYC 비니", 70, 12_900),
            TopSellingProductDto(5L, "털방울 비니", 60, 19_900)
        )

        return ResponseEntity.ok(TopSellingProductResponse(products))
    }
}
