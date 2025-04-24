package kr.hhplus.be.server.application.ranking

import java.math.BigDecimal

data class BestSellingProductsResult(
    val productId: Long,
    val salesCount: Long,
    val name: String,
    val price: BigDecimal
)
