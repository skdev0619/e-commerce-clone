package kr.hhplus.be.server.interfaces.ranking

import kr.hhplus.be.server.application.ranking.TopSellingProductsQueryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/ranking")
class RankingController(
    private val topSellingProductsQueryService: TopSellingProductsQueryService
) : RankingApiSpecification {

    @GetMapping("/top-selling-products")
    override fun topSellingProducts(@RequestBody request: TopSellingProductsRequest): ResponseEntity<List<TopSellingProductsResponse>> {
        val products = topSellingProductsQueryService.findTopSellingProducts(
            TopSellingProductsRequest.from(request)
        )

        return ResponseEntity.ok(products.map{ TopSellingProductsResponse(it.productId, it.sales)})
    }
}
