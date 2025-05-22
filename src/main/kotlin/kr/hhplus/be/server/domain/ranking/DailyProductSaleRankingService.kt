package kr.hhplus.be.server.domain.ranking

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class DailyProductSaleRankingService(
    private val dailyProductSaleRankingRepository: DailyProductSaleRankingRepository
) {
    fun accumulate(baseDate: LocalDate, productSales: List<ProductSale>) {
        productSales.forEach { it ->
            dailyProductSaleRankingRepository.add(
                baseDate,
                it.productId,
                it.salesCount
            )
        }
    }

    fun get(baseDate: LocalDate): DailyProductSaleRanking? {
        return dailyProductSaleRankingRepository.getRanking(baseDate)
    }

    fun get(baseDate: LocalDate, count : Long) : DailyProductSaleRanking? {
        return dailyProductSaleRankingRepository.getRanking(baseDate)
    }
}
