package kr.hhplus.be.server.interfaces.product

import kr.hhplus.be.server.application.product.ProductQueryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/products")
class ProductController(
    private val productService: ProductQueryService
) : ProductApiSpecification {

    @GetMapping("/{id}")
    override fun product(@PathVariable id: Long): ResponseEntity<ProductViewResponse> {
        require(id > 0) { "상품의 id는 1 이상이어야 합니다" }
        val product = productService.findById(id)
        return ResponseEntity.ok(ProductViewResponse.from(product))
    }

    @GetMapping
    override fun allProduct(): ResponseEntity<List<ProductViewResponse>> {
        val products = productService.findAll()
        val response = products.map { ProductViewResponse.from(it) }
        return ResponseEntity.ok(response)
    }
}
