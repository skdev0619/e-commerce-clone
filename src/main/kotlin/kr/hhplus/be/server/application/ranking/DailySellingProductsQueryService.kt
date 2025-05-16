package kr.hhplus.be.server.application.ranking

import kr.hhplus.be.server.domain.product.ProductService
import kr.hhplus.be.server.domain.ranking.DailyProductSaleRankingService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Transactional(readOnly = true)
@Service
class DailySellingProductsQueryService(
    private val dailyProductSaleRankingService: DailyProductSaleRankingService,
    private val productService: ProductService
) {

    fun getRanking(baseDate: LocalDate, limit: Long): List<BestSellingProductsResult> {
        val dailySales = dailyProductSaleRankingService.get(baseDate, limit) ?: return emptyList()

        val productIds = dailySales.ranking.map { it.productId }
        val products = productService.findByIdIn(productIds)

        return dailySales.ranking.mapNotNull { sale ->
            val product = products.find { it.id == sale.productId }
            product?.let {
                BestSellingProductsResult(it.id, sale.salesCount, it.name, it.price)
            }
        }
    }
}