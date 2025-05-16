package kr.hhplus.be.server.domain.ranking

import java.time.LocalDate

data class ProductSale(val productId: Long, val salesCount: Long)

class DailyProductSaleRanking(
    val baseDate: LocalDate,
    val ranking: List<ProductSale>
)