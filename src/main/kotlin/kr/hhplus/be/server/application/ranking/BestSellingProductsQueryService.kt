package kr.hhplus.be.server.application.ranking//package kr.hhplus.be.server.application.ranking

import kr.hhplus.be.server.application.cache.GlobalCacheable
import kr.hhplus.be.server.domain.ranking.BestSellingProductService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class BestSellingProductsQueryService(
    private val bestSellingProductService: BestSellingProductService
) {

    @GlobalCacheable(
        name = "best-selling-products",
        key = "T(java.time.LocalDate).now().toString()",
        expireMinute = 1560    //26시간
    )
    fun findBestSellingProducts(query: BestSellingProductsQuery): List<BestSellingProductsResult> {
        val products = bestSellingProductService.findBestSellingProducts(query.startDate, query.endDate, query.limit)
        return products.map { BestSellingProductsResult(it.productId, it.salesCount, it.name, it.price) }
    }
}
