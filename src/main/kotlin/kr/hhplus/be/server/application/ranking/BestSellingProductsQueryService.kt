package kr.hhplus.be.server.application.ranking//package kr.hhplus.be.server.application.ranking

import kr.hhplus.be.server.domain.ranking.BestSellingProductService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BestSellingProductsQueryService(
    private val bestSellingProductService: BestSellingProductService
) {

    fun findBestSellingProducts(query: BestSellingProductsQuery): List<BestSellingProductsResult> {
        val products = bestSellingProductService.findBestSellingProducts(query.startDate, query.endDate, query.limit)
        return products.map { BestSellingProductsResult(it.productId, it.salesCount, it.name, it.price) }
    }
}
