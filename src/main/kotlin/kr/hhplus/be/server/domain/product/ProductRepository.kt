package kr.hhplus.be.server.domain.product

interface ProductRepository {
    fun save(product: Product): Product
    fun findById(id: Long): Product?
    fun findAll(): List<Product>
    fun findByIdIn(ids: List<Long>): List<Product>
    fun findByIdWithLock(id : Long) : Product?
    fun findByIdInWithLock(ids: List<Long>): List<Product>
}
