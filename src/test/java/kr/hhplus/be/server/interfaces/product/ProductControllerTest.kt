package kr.hhplus.be.server.interfaces.product

import kr.hhplus.be.server.application.product.ProductQueryService
import kr.hhplus.be.server.application.product.ProductViewResult
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.math.BigDecimal

@WebMvcTest(ProductController::class)
class ProductControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var productService: ProductQueryService

    @DisplayName("상품 id로 상품 조회 시, 상품 정보를 반환한다")
    @Test
    fun findById() {
        val id = 1L
        val product = ProductViewResult(id, "검정 볼캡", BigDecimal(10000), 100)
        val response = ProductViewResponse.from(product)

        `when`(productService.findById(id)).thenReturn(product)

        mockMvc.get("/api/v1/products/${id}") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$.id") { value(response.id.toInt()) }
            jsonPath("$.name") { value(response.name) }
            jsonPath("$.price") { value(response.price.toInt()) }
            jsonPath("$.stock") { value(response.stock) }
        }

        verify(productService).findById(id)
    }

    @DisplayName("상품 id가 0이하면 bad request 를 반환한다")
    @Test
    fun badRequest() {
        val id = 0L

        mockMvc.get("/api/v1/products/${id}") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isBadRequest() }
            jsonPath("$.code") { value(400) }
            jsonPath("$.message") { value("상품의 id는 1 이상이어야 합니다") }
        }

        verify(productService, times(0)).findById(id)
    }

    @DisplayName("존재하지 않는 상품 id로 조회하면 not found Error 를 반환한다")
    @Test
    fun findByNonExistId() {
        val id = 11L

        `when`(productService.findById(id))
            .thenThrow(NoSuchElementException("존재하지 않는 상품입니다. id : ${id}"))

        mockMvc.get("/api/v1/products/${id}") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound() }
            jsonPath("$.code") { value(404) }
            jsonPath("$.message") { value("존재하지 않는 상품입니다. id : ${id}") }
        }

        verify(productService).findById(id)
    }

    @DisplayName("모든 상품 정보를 반환한다")
    @Test
    fun findAll() {
        val products = listOf(
            ProductViewResult(1L, "검정볼캡", BigDecimal(10_000), 100)
        )
        val response = products.map { ProductViewResponse.from(it) }

        `when`(productService.findAll()).thenReturn(products)

        mockMvc.get("/api/v1/products")
            .andExpect {
                status { isOk() }
                jsonPath("$[0].id") { value(response[0].id.toInt()) }
                jsonPath("$[0].name") { value(response[0].name) }
                jsonPath("$[0].price") { value(response[0].price.toInt()) }
                jsonPath("$[0].stock") { value(response[0].stock) }
            }

        verify(productService).findAll()
    }
}
