package kr.hhplus.be.server.interfaces.ranking

import com.fasterxml.jackson.databind.ObjectMapper
import kr.hhplus.be.server.application.ranking.TopSellingProductsQueryResult
import kr.hhplus.be.server.application.ranking.TopSellingProductsQueryService
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
import java.time.LocalDateTime

@WebMvcTest(RankingController::class)
class RankingControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockitoBean
    private lateinit var topSellingProductsQueryService: TopSellingProductsQueryService

    @DisplayName("인기 상품 N건을 조회 요청 시, 조회 결과를 반환한다")
    @Test
    fun topSellingProducts() {
        val request = TopSellingProductsRequest(
            LocalDateTime.of(2025, 4, 13, 0, 0),
            LocalDateTime.of(2025, 4, 15, 23, 59),
            2
        )
        val command = TopSellingProductsRequest.from(request)
        val result = listOf(
            TopSellingProductsQueryResult(1L, 100),
            TopSellingProductsQueryResult(2L, 90)
        )

        `when`(topSellingProductsQueryService.findTopSellingProducts(command))
            .thenReturn(result)

        mockMvc.get("/api/v1/ranking/top-selling-products") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isOk() }
            jsonPath("$[0].productId") { value(result[0].productId.toInt()) }
            jsonPath("$[0].sales") { value(result[0].sales.toInt()) }
            jsonPath("$[1].productId") { value(result[1].productId.toInt()) }
            jsonPath("$[1].sales") { value(result[1].sales.toInt()) }
        }

        verify(topSellingProductsQueryService).findTopSellingProducts(command)
    }
}
