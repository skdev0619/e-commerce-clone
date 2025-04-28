package kr.hhplus.be.server.domain.ranking

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalTime

@Service
class DailyProductSaleService(
    private val dailyProductSaleRepository: DailyProductSaleRepository
) {
    @Transactional
    fun bulkSave(dailyProductSales: List<DailyProductSale>) {
        dailyProductSaleRepository.saveAll(dailyProductSales)
    }

    @Transactional(readOnly = true)
    fun findBestSellingProducts(startDate: LocalDate, endDate: LocalDate, limit: Int): List<BestSellingProductSalesInfo> {
        val pageable = PageRequest.ofSize(limit)
        return dailyProductSaleRepository.findBestSellingProductsBy(startDate, endDate, pageable)
    }
}