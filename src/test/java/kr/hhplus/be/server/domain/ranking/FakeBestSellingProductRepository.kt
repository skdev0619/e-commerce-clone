package kr.hhplus.be.server.domain.ranking

import org.springframework.data.domain.Pageable
import java.time.LocalDate
import java.util.concurrent.ConcurrentHashMap

class FakeBestSellingProductRepository : BestSellingProductRepository {

    private val bestSellingProducts = ConcurrentHashMap<Long, BestSellingProduct>()

    override fun findByIdIn(ids: List<Long>): List<BestSellingProduct> {
        return bestSellingProducts.values.filter { it.id in ids }
    }

    override fun saveAll(products: List<BestSellingProduct>): List<BestSellingProduct> {
        products.forEach {
            bestSellingProducts.putIfAbsent(it.id, it)
        }
        return products
    }

    override fun findByPeriod(startDate: LocalDate, endDate: LocalDate, pageable: Pageable): List<BestSellingProduct> {
        return bestSellingProducts.values
            .filter { it.startDate == startDate && it.endDate == endDate }
            .take(pageable.pageSize)
    }


}
