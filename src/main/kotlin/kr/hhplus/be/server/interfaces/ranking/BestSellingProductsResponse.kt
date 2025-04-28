package kr.hhplus.be.server.interfaces.ranking

import kr.hhplus.be.server.application.ranking.BestSellingProductsResult
import java.math.BigDecimal

data class BestSellingProductsResponse(
    val productId: Long,
    val name: String,
    val salesCount: Long,
    val price: BigDecimal
) {
    fun from(result: BestSellingProductsResult): BestSellingProductsResponse {
        return BestSellingProductsResponse(result.productId, result.name, result.salesCount, result.price)
    }
}
