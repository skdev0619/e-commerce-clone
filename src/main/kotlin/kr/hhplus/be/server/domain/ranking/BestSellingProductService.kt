package kr.hhplus.be.server.domain.ranking

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class BestSellingProductService(
    private val bestSellingProductRepository: BestSellingProductRepository
) {

    @Transactional
    fun bulkSave(products: List<BestSellingProduct>): List<BestSellingProduct> {
        return bestSellingProductRepository.saveAll(products)
    }

    @Transactional(readOnly = true)
    fun findBestSellingProducts(startDate: LocalDate, endDate: LocalDate, limit: Int): List<BestSellingProduct> {
        return bestSellingProductRepository.findByPeriod(startDate, endDate, Pageable.ofSize(limit))
    }
}
