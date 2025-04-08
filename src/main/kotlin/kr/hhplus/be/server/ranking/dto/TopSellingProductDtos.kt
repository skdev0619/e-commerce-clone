package kr.hhplus.be.server.ranking.dto

data class TopSellingProductResponse(
    val products: List<TopSellingProductDto>
)

data class TopSellingProductDto(
    val id: Long,
    val name: String,
    val sales: Int,
    val price: Int
)
