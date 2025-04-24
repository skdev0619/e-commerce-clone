package kr.hhplus.be.server.infrastructure.product

import kr.hhplus.be.server.domain.product.Product
import kr.hhplus.be.server.domain.product.ProductRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class ProductRepositoryImpl(
    private val jpaRepository: ProductJpaRepository
) : ProductRepository {

    override fun save(product: Product): Product {
        return jpaRepository.save(product)
    }

    override fun findById(id: Long): Product? {
        return jpaRepository.findByIdOrNull(id)
    }

    override fun findAll(): List<Product> {
        return jpaRepository.findAll()
    }

    override fun findByIdIn(ids: List<Long>): List<Product> {
        return jpaRepository.findAllById(ids)
    }

    override fun findByIdInWithLock(ids: List<Long>): List<Product> {
        return jpaRepository.findByIdInWithLock(ids)
    }
}
