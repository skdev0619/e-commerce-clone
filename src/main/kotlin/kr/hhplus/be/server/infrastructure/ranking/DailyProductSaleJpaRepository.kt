package kr.hhplus.be.server.infrastructure.ranking

import kr.hhplus.be.server.domain.ranking.BestSellingProductSalesInfo
import kr.hhplus.be.server.domain.ranking.DailyProductSale
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface DailyProductSaleJpaRepository : JpaRepository<DailyProductSale, Long> {

    @Query(
        "SELECT new kr.hhplus.be.server.domain.ranking.BestSellingProductSalesInfo(d.productId, sum(d.salesCount)) " +
                "FROM DailyProductSale d " +
                "WHERE d.baseDate BETWEEN :startTime AND :endTime " +
                "GROUP BY d.productId "
    )
    fun getBestSellingProductsBy(
        @Param("startTime") startDate: LocalDate,
        @Param("endTime") endDate: LocalDate,
        pageable: Pageable
    ): List<BestSellingProductSalesInfo>
}
