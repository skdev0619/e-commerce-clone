package kr.hhplus.be.server.application.ranking

import kr.hhplus.be.server.domain.order.OrderProductSalesInfo

data class TopSellingProductsQueryResult(
    val productId: Long,
    val sales: Long
) {
    companion object {
        fun from(salesInfo: OrderProductSalesInfo): TopSellingProductsQueryResult {
            return TopSellingProductsQueryResult(salesInfo.productId, salesInfo.sales)
        }
    }
}