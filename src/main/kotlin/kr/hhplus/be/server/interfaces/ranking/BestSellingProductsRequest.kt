package kr.hhplus.be.server.interfaces.ranking

import kr.hhplus.be.server.application.ranking.BestSellingProductsQuery
import java.time.LocalDate

data class BestSellingProductsRequest(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val limit: Int = 5
) {
    companion object {
        fun from(request: BestSellingProductsRequest): BestSellingProductsQuery {
            return BestSellingProductsQuery(request.startDate, request.endDate, request.limit)
        }
    }
}

