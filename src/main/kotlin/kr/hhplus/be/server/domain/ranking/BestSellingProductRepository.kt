package kr.hhplus.be.server.domain.ranking

import org.springframework.data.domain.Pageable
import java.time.LocalDate

interface BestSellingProductRepository {
    fun findByIdIn(ids: List<Long>): List<BestSellingProduct>
    fun saveAll(products: List<BestSellingProduct>): List<BestSellingProduct>
    fun findByPeriod(startDate: LocalDate, endDate: LocalDate, pageable: Pageable): List<BestSellingProduct>
}