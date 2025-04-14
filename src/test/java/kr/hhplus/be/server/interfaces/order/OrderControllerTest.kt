package kr.hhplus.be.server.interfaces.order

import com.fasterxml.jackson.databind.ObjectMapper
import kr.hhplus.be.server.application.order.OrderCompletedResult
import kr.hhplus.be.server.application.order.OrderFacade

import kr.hhplus.be.server.application.order.OrderItemResult
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.math.BigDecimal
import java.time.LocalDateTime

@WebMvcTest(OrderController::class)
class OrderControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockitoBean
    private lateinit var orderFacade: OrderFacade

    @DisplayName("주문 생성 시, 생성된 주문 결과를 반환한다")
    @Test
    fun createOrder() {
        val userId = 1L
        val issueCouponId = 5L
        val orderItem = listOf(
            OrderItemRequest(1L, 1, 5_000)
        )
        val request = OrderCreateRequest(userId, orderItem, issueCouponId)
        val command = OrderCreateRequest.from(request)
        val response = OrderCompletedResult(
            1L, userId, "ACTIVE", null, LocalDateTime.now(),
            listOf(OrderItemResult(1L, 1, BigDecimal(5_000))), BigDecimal(5_000), 6L
        )

        `when`(orderFacade.createOrder(command))
            .thenReturn(response)

        mockMvc.post("/api/v1/orders") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isCreated() }
            jsonPath("$.userId") { value(userId.toInt()) }
            jsonPath("$.status") { value(response.status) }
            jsonPath("$.totalPrice") { value(response.totalPrice.toInt()) }
            jsonPath("$.paymentId") { value(response.paymentId.toInt()) }
        }

        verify(orderFacade).createOrder(command)
    }

    @DisplayName("상품의 재고가 부족하면 500 error 를 반환한다")
    @Test
    fun insufficientStock() {
        val userId = 1L
        val issueCouponId = 5L
        val orderItem = listOf(
            OrderItemRequest(1L, 1, 5_000)
        )
        val request = OrderCreateRequest(userId, orderItem, issueCouponId)
        val command = OrderCreateRequest.from(request)

        `when`(orderFacade.createOrder(command))
            .thenThrow(IllegalStateException("상품의 재고가 부족합니다"))

        mockMvc.post("/api/v1/orders") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isInternalServerError() }
            jsonPath("$.code") { value(500) }
            jsonPath("$.message") { value("상품의 재고가 부족합니다") }
        }

        verify(orderFacade).createOrder(command)
    }

    @DisplayName("잔액이 부족하면 500 error 를 반환한다")
    @Test
    fun insufficientCash() {
        val userId = 1L
        val issueCouponId = 5L
        val orderItem = listOf(
            OrderItemRequest(1L, 1000, 5_000)
        )
        val request = OrderCreateRequest(userId, orderItem, issueCouponId)
        val command = OrderCreateRequest.from(request)

        `when`(orderFacade.createOrder(command))
            .thenThrow(IllegalStateException("잔액이 부족합니다"))

        mockMvc.post("/api/v1/orders") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isInternalServerError() }
            jsonPath("$.code") { value(500) }
            jsonPath("$.message") { value("잔액이 부족합니다") }
        }

        verify(orderFacade).createOrder(command)
    }
}