package kr.hhplus.be.server.product.presentation

import kr.hhplus.be.server.product.presentation.dto.ProductDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/products")
class ProductController : ProductApiSpecification {

    @GetMapping("/{id}")
    override fun product(@PathVariable id: Long): ResponseEntity<ProductDto> {
        val response = ProductDto(id, "회색 비니", 100)
        return ResponseEntity.ok(response)
    }
}
