package kr.hhplus.be.server.infrastructure.ranking

import kr.hhplus.be.server.domain.ranking.BestSellingProduct
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface BestSellingProductJpaRepository : JpaRepository<BestSellingProduct, Long> {
    fun findByStartDateAndEndDate(
        startDate: LocalDate,
        endDate: LocalDate,
        pageable: Pageable
    ): List<BestSellingProduct>
}