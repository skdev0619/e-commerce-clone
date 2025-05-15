package kr.hhplus.be.server.interfaces.ranking

import kr.hhplus.be.server.application.ranking.BestSellingProductsQueryService
import kr.hhplus.be.server.application.ranking.DailySellingProductsQueryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/v1/ranking")
class RankingController(
    private val topSellingProductsQueryService: BestSellingProductsQueryService,
    private val dailySellingProductsQueryService: DailySellingProductsQueryService
) : RankingApiSpecification {

    @GetMapping("/top-selling-products")
    override fun topSellingProducts(request: BestSellingProductsRequest): ResponseEntity<List<BestSellingProductsResponse>> {
        val products = topSellingProductsQueryService.findBestSellingProducts(
            BestSellingProductsRequest.from(request)
        )

        return ResponseEntity.ok(products.map {
            BestSellingProductsResponse(
                it.productId,
                it.name,
                it.salesCount,
                it.price
            )
        })
    }

    @GetMapping("/daily-top-selling-products")
    override fun dailyTopSellingProducts(
        baseDate: LocalDate,
        limit: Long
    ): ResponseEntity<List<BestSellingProductsResponse>> {
        val products = dailySellingProductsQueryService.getRanking(baseDate, limit)

        return ResponseEntity.ok(products.map {
            BestSellingProductsResponse(
                it.productId,
                it.name,
                it.salesCount,
                it.price
            )
        })
    }
}
