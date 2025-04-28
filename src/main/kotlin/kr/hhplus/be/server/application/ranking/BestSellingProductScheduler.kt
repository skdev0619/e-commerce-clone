package kr.hhplus.be.server.application.ranking

import kr.hhplus.be.server.domain.order.OrderSummaryService
import kr.hhplus.be.server.domain.product.ProductService
import kr.hhplus.be.server.domain.ranking.BestSellingProduct
import kr.hhplus.be.server.domain.ranking.BestSellingProductService
import kr.hhplus.be.server.domain.ranking.DailyProductSale
import kr.hhplus.be.server.domain.ranking.DailyProductSaleService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Transactional
@Service
class BestSellingProductScheduler(
    private val orderSummaryService: OrderSummaryService,
    private val dailyProductSaleService: DailyProductSaleService,
    private val bestSellingProductService: BestSellingProductService,
    private val productService: ProductService
) {

    companion object {
        const val SUMMARY_START_DAY_OFFSET = 3L
        const val SUMMARY_END_DAY_OFFSET = 1L
        const val PAGE_SIZE = 5
    }

    @Scheduled(cron = "0 0 2 * * ?")
    fun runScheduled() {
        runDailyTask()
    }

    fun runDailyTask(
        summaryStartDate: LocalDate = LocalDate.now().minusDays(SUMMARY_START_DAY_OFFSET),
        summaryEndDate: LocalDate = LocalDate.now().minusDays(SUMMARY_END_DAY_OFFSET)
    ) {
        val dailyProductSales = getDailyProductSalesBy(summaryEndDate)
        dailyProductSaleService.bulkSave(dailyProductSales)

        val bestSellingProducts = getBestSellingProductsBy(summaryStartDate, summaryEndDate, PAGE_SIZE)
        bestSellingProductService.bulkSave(bestSellingProducts)
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
        val topSales = dailyProductSaleService.findBestSellingProducts(startDate, endDate, pageSize)
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
