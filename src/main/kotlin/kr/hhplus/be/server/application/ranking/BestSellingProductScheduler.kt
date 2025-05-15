package kr.hhplus.be.server.application.ranking

import kr.hhplus.be.server.application.cache.CacheTemplate
import kr.hhplus.be.server.domain.order.OrderSummaryService
import kr.hhplus.be.server.domain.product.ProductService
import kr.hhplus.be.server.domain.ranking.*
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDate

@Transactional
@Service
class BestSellingProductScheduler(
    private val orderSummaryService: OrderSummaryService,
    private val dailyProductSaleAggregateService: DailyProductSaleAggregateService,
    private val bestSellingProductService: BestSellingProductService,
    private val productService: ProductService,
    private val cacheTemplate: CacheTemplate<String, Any>
) {

    companion object {
        const val SUMMARY_START_DAY_OFFSET = 3L
        const val SUMMARY_END_DAY_OFFSET = 1L
        const val PAGE_SIZE = 5
    }

    private val cacheVersion = LocalDate.now().plusDays(1).toString()

    @Scheduled(cron = "0 0 22 * * ?")
    fun runScheduled() {
        runDailyTask()
    }

    fun runDailyTask(
        summaryStartDate: LocalDate = LocalDate.now().minusDays(SUMMARY_START_DAY_OFFSET),
        summaryEndDate: LocalDate = LocalDate.now().minusDays(SUMMARY_END_DAY_OFFSET)
    ) {
        putFailOverCache()
        val dailyProductSales = getDailyProductSalesBy(summaryEndDate)
        dailyProductSaleAggregateService.bulkSave(dailyProductSales)

        val bestSellingProducts = getBestSellingProductsBy(summaryStartDate, summaryEndDate, PAGE_SIZE)
        bestSellingProductService.bulkSave(bestSellingProducts)
        putCache(bestSellingProducts)
    }

    private fun putFailOverCache() {
        //집계 시작 전, 현재 캐시 데이터를 failover cache에 담는다
        val currentVersion = LocalDate.now().toString()
        val cachedProducts = cacheTemplate.get("best-selling-products:${currentVersion}")

        cachedProducts?.let{
            val cacheName = "best-selling-products:${cacheVersion}-failover"
            cacheTemplate.put(cacheName, cachedProducts, Duration.ofHours(26))
        }
    }

    private fun putCache(products: List<BestSellingProduct>) {
        cacheTemplate.put("best-selling-products:${cacheVersion}", products, Duration.ofHours(26))
    }

    private fun getDailyProductSalesBy(baseDate: LocalDate): List<DailyProductSale> {
        val productSalesSummaries = orderSummaryService.getProductSalesCountBy(baseDate)
        return productSalesSummaries.map { DailyProductSale(baseDate, it.productId, it.sales) }
    }

    private fun getBestSellingProductsBy(
        startDate: LocalDate,
        endDate: LocalDate,
        pageSize: Int
    ): List<BestSellingProduct> {
        val topSales = dailyProductSaleAggregateService.findBestSellingProducts(startDate, endDate, pageSize)
        val products = productService.findByIdIn(topSales.map { it.productId })

        return topSales.map { sales ->
            val product = products.first { it.id == sales.productId }

            BestSellingProduct(
                startDate = startDate,
                endDate = endDate,
                productId = product.id,
                salesCount = sales.salesCount,
                name = product.name,
                price = product.price
            )
        }
    }
}
