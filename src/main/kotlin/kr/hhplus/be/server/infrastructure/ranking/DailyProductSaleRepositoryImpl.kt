package kr.hhplus.be.server.infrastructure.ranking

import kr.hhplus.be.server.domain.ranking.BestSellingProductSalesInfo
import kr.hhplus.be.server.domain.ranking.DailyProductSale
import kr.hhplus.be.server.domain.ranking.DailyProductSaleRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class DailyProductSaleRepositoryImpl(
    private val jpaRepository: DailyProductSaleJpaRepository
) : DailyProductSaleRepository {

    override fun findByIdIn(ids: List<Long>): List<DailyProductSale> {
        return jpaRepository.findAllById(ids)
    }

    override fun saveAll(dailyProductSales: List<DailyProductSale>): List<DailyProductSale> {
        return jpaRepository.saveAll(dailyProductSales)
    }

    override fun findBestSellingProductsBy(
        startDate: LocalDate,
        endDate: LocalDate,
        pageable: Pageable
    ): List<BestSellingProductSalesInfo> {
        return jpaRepository.getBestSellingProductsBy(startDate, endDate, pageable)
    }
}
