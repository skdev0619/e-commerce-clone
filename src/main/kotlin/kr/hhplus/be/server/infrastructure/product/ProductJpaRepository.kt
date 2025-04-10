package kr.hhplus.be.server.infrastructure.product

import kr.hhplus.be.server.domain.product.Product
import kr.hhplus.be.server.domain.product.ProductRepository
import org.springframework.stereotype.Repository

@Repository
class ProductJpaRepository : ProductRepository {

    override fun save(product: Product): Product {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): Product? {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<Product> {
        TODO("Not yet implemented")
    }

    override fun findByIdIn(ids: List<Long>): List<Product> {
        TODO("Not yet implemented")
    }
}