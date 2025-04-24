package kr.hhplus.be.server.domain.ranking

import org.springframework.data.domain.Pageable
import java.time.LocalDate
import java.util.concurrent.ConcurrentHashMap

class FakeDailyProductSaleRepository : DailyProductSaleRepository {

    private val dailyProductInfos = ConcurrentHashMap<Long, DailyProductSale>()

    override fun findByIdIn(ids: List<Long>): List<DailyProductSale> {
        return dailyProductInfos.values.filter { it.id in ids }
    }

    override fun saveAll(dailyProductSales: List<DailyProductSale>): List<DailyProductSale> {
        dailyProductSales.forEach {
            dailyProductInfos.putIfAbsent(it.id, it)
        }
        return dailyProductSales
    }

    override fun findBestSellingProductsBy(
        startDate: LocalDate,
        endDate: LocalDate,
        pageable: Pageable
    ): List<BestSellingProductSalesInfo> {
        return dailyProductInfos.values
            .filter { it.baseDate in startDate..endDate }
            .groupBy { it.productId }
            .map { (productId, records) ->
                val totalSales = records.sumOf { it.salesCount }
                BestSellingProductSalesInfo(productId, totalSales)
            }
    }
}