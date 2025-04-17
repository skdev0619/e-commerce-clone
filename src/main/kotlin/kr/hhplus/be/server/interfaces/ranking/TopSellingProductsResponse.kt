package kr.hhplus.be.server.interfaces.ranking

import kr.hhplus.be.server.application.ranking.TopSellingProductsQueryResult

data class TopSellingProductsResponse(
    val productId: Long,
    val sales: Long
) {
    fun from(result: TopSellingProductsQueryResult): TopSellingProductsResponse {
        return TopSellingProductsResponse(result.productId, result.sales)
    }
}
