package kr.hhplus.be.server.domain.product

import java.util.concurrent.ConcurrentHashMap

class FakeProductRepository : ProductRepository {

    private val products = ConcurrentHashMap<Long, Product>()

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

    override fun findByIdIn(ids: List<Long>): List<Product> {
        return products.values.filter { ids.contains(it.id) }
    }

    override fun findByIdWithLock(id: Long): Product? {
        return findById(id)
    }

    override fun findByIdInWithLock(ids: List<Long>): List<Product> {
        return findByIdIn(ids)
    }
}
