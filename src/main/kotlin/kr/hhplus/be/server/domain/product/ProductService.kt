package kr.hhplus.be.server.domain.product

import org.springframework.stereotype.Service

@Service
class ProductService(
    val productRepository: ProductRepository
) {

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
        val products = productRepository.findByIdIn(productIds)

        for (product in products) {
            productQuantities.find { it.productId == product.id }?.let {
                val quantity = it.quantity
                product.decreaseStock(quantity)
            }
        }
    }
}
