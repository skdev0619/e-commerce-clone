package kr.hhplus.be.server.domain.ranking

import java.time.LocalDate

interface DailyProductSaleRankingRepository {
    fun add(baseDate: LocalDate, productId: Long, quantity: Long)
    fun getRanking(baseDate: LocalDate) : DailyProductSaleRanking?
    fun getRanking(baseDate: LocalDate, count: Long) : DailyProductSaleRanking?
}
