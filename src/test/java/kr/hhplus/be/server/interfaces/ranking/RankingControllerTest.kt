package kr.hhplus.be.server.interfaces.ranking

import com.fasterxml.jackson.databind.ObjectMapper
import kr.hhplus.be.server.application.ranking.BestSellingProductsQueryService
import kr.hhplus.be.server.application.ranking.BestSellingProductsResult
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.math.BigDecimal
import java.time.LocalDate

@WebMvcTest(RankingController::class)
class RankingControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockitoBean
    private lateinit var topSellingProductsQueryService: BestSellingProductsQueryService

    @DisplayName("특정 기간의 인기 상품 N건을 조회 요청 시, 조회 결과를 반환한다")
    @Test
    fun bestSellingProducts() {
        val request = BestSellingProductsRequest(
            LocalDate.of(2025, 4, 13),
            LocalDate.of(2025, 4, 15),
            2
        )
        val command = BestSellingProductsRequest.from(request)
        val result = listOf(
            BestSellingProductsResult(productId = 1L, salesCount = 100, name = "무지볼캡", price = BigDecimal(15_000)),
            BestSellingProductsResult(productId = 2L, salesCount = 100, name = "검정버킷햇", price = BigDecimal(17_000))
        )

        `when`(topSellingProductsQueryService.findBestSellingProducts(command))
            .thenReturn(result)

        mockMvc.get("/api/v1/ranking/top-selling-products") {
            param("startDate", request.startDate.toString())
            param("endDate", request.endDate.toString())
            param("limit", request.limit.toString())
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$[0].productId") { value(result[0].productId.toInt()) }
            jsonPath("$[0].salesCount") { value(result[0].salesCount.toInt()) }
            jsonPath("$[0].name") { value(result[0].name) }
            jsonPath("$[0].price") { value(result[0].price.toInt()) }

            jsonPath("$[1].productId") { value(result[1].productId.toInt()) }
            jsonPath("$[1].salesCount") { value(result[1].salesCount.toInt()) }
            jsonPath("$[1].name") { value(result[1].name) }
            jsonPath("$[1].price") { value(result[1].price.toInt()) }
        }

        verify(topSellingProductsQueryService).findBestSellingProducts(command)
    }
}
