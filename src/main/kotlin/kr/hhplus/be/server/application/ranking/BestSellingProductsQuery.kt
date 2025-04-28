package kr.hhplus.be.server.application.ranking

import java.time.LocalDate

data class BestSellingProductsQuery(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val limit: Int
)