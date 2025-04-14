package kr.hhplus.be.server.application.product

import kr.hhplus.be.server.domain.product.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class ProductQueryService(
    val productRepository: ProductRepository
) {
    fun findById(id: Long): ProductViewResult {
        val product = (productRepository.findById(id)
            ?: throw NoSuchElementException("존재하지 않는 상품입니다. id : ${id}"))
        return ProductViewResult.from(product)
    }

    fun findAll(): List<ProductViewResult> {
        val products = productRepository.findAll()
        return products.map { product -> ProductViewResult.from(product) }
    }
}
