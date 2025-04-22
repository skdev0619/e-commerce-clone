package kr.hhplus.be.server.application.ranking

import java.time.LocalDateTime

data class TopSellingProductsQuery(
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val limit: Int
)