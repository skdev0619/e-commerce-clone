package kr.hhplus.be.server.domain.ranking

import org.springframework.data.domain.Pageable
import java.time.LocalDate

interface DailyProductSaleRepository {
    fun findByIdIn(ids: List<Long>): List<DailyProductSale>
    fun saveAll(dailyProductSales: List<DailyProductSale>): List<DailyProductSale>
    fun findBestSellingProductsBy(
        startDate: LocalDate,
        endDate: LocalDate,
        pageable: Pageable
    ): List<BestSellingProductSalesInfo>
}