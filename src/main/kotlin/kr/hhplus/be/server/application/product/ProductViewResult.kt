package kr.hhplus.be.server.application.product

import kr.hhplus.be.server.domain.product.Product
import java.math.BigDecimal

data class ProductViewResult(
    val id: Long,
    val name: String,
    val price: BigDecimal,
    var stock: Int,
) {
    companion object {
        fun from(product: Product): ProductViewResult {
            return ProductViewResult(product.id, product.name, product.price, product.stock)
        }
    }
}
