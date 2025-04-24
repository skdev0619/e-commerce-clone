package kr.hhplus.be.server.domain.product

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ProductService(
    val productRepository: ProductRepository
) {
    @Transactional(readOnly = true)
    fun validateStock(productQuantities: List<ProductQuantity>) {
        val productIds = productQuantities.map { it.productId }
        val products = productRepository.findByIdIn(productIds)

        for (product in products) {
            productQuantities.find { it.productId == product.id }?.let {
                val quantity = it.quantity
                product.validateStock(quantity)
            }
        }
    }

    fun decreaseStock(productQuantities: List<ProductQuantity>) {
        val productIds = productQuantities.map { it.productId }
        val products = productRepository.findByIdInWithLock(productIds)

        for (product in products) {
            productQuantities.find { it.productId == product.id }?.let {
                val quantity = it.quantity
                product.decreaseStock(quantity)
            }
        }
    }

    @Transactional(readOnly = true)
    fun findByIdIn(ids: List<Long>): List<Product> {
        return productRepository.findByIdIn(ids)
    }
}
