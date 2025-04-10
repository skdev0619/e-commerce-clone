package kr.hhplus.be.server.domain.product

import org.springframework.stereotype.Service

@Service
class ProductService(
    val productRepository: ProductRepository
) {
    fun decreaseStock(productQuantityPairs: List<Pair<Long, Int>>) {
        productQuantityPairs.forEach { (productId, quantity) ->
            val product = productRepository.findById(productId)
                ?: throw NoSuchElementException("존재하지 않는 상품입니다.")

            product.decreaseStock(quantity)
        }
    }
}
