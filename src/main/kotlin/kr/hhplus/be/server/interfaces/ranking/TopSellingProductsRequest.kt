package kr.hhplus.be.server.interfaces.ranking

import kr.hhplus.be.server.application.ranking.TopSellingProductsQuery
import java.time.LocalDateTime

data class TopSellingProductsRequest(
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val limit: Int = 5
) {
    companion object {
        fun from(request: TopSellingProductsRequest): TopSellingProductsQuery {
            return TopSellingProductsQuery(request.startDate, request.endDate, request.limit)
        }
    }
}

