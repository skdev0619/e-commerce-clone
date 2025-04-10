package kr.hhplus.be.server.domain.product

import java.util.concurrent.ConcurrentHashMap

class FakeProductRepository : ProductRepository {

    val products = ConcurrentHashMap<Long, Product>()

    override fun save(product: Product): Product {
        products.put(product.id, product)
        return product
    }

    override fun findById(id: Long): Product? {
        return products.get(id)
    }

    override fun findAll(): List<Product> {
        return products.values.toList()
    }
}
