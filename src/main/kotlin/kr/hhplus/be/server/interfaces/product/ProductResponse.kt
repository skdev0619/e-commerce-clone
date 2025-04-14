package kr.hhplus.be.server.interfaces.product

import kr.hhplus.be.server.application.product.ProductViewResult
import java.math.BigDecimal

data class ProductViewResponse(
    val id: Long,
    val name: String,
    val price: BigDecimal,
    val stock: Int
) {
    companion object {
        fun from(product: ProductViewResult): ProductViewResponse {
            return ProductViewResponse(product.id, product.name, product.price, product.stock)
        }
    }
}

