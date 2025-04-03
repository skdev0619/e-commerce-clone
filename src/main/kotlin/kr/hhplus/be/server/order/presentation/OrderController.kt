package kr.hhplus.be.server.order.presentation

import kr.hhplus.be.server.order.presentation.dto.OrderCreateRequest
import kr.hhplus.be.server.order.presentation.dto.OrderCreateResponse
import kr.hhplus.be.server.order.presentation.dto.OrderItemDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/v1/orders")
class OrderController : OrderApiSpecification {

    @PostMapping
    override fun createOrder(@RequestBody request: OrderCreateRequest): ResponseEntity<OrderCreateResponse> {
        val response = OrderCreateResponse(
            1L,
            listOf(
                OrderItemDto(1L, 1, 10_000),
                OrderItemDto(2L, 2, 20_000)
            ),
            50_000,
            5_000,
            45_000
        )
        return ResponseEntity.created(URI.create("api/v1/orders/${response.id}")).body(response)
    }
}
