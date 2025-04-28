package kr.hhplus.be.server.infrastructure.ranking

import kr.hhplus.be.server.domain.ranking.BestSellingProduct
import kr.hhplus.be.server.domain.ranking.BestSellingProductRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class BestSellingProductRepositoryImpl(
    private val jpaRepository: BestSellingProductJpaRepository
) : BestSellingProductRepository {

    override fun findByIdIn(ids: List<Long>): List<BestSellingProduct> {
        return jpaRepository.findAllById(ids)
    }

    override fun saveAll(products: List<BestSellingProduct>): List<BestSellingProduct> {
        return jpaRepository.saveAll(products)
    }

    override fun findByPeriod(startDate: LocalDate, endDate: LocalDate, pageable: Pageable): List<BestSellingProduct> {
        return jpaRepository.findByStartDateAndEndDate(startDate, endDate, pageable)
    }
}
