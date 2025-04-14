package kr.hhplus.be.server.interfaces.order

import kr.hhplus.be.server.application.order.OrderFacade
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/v1/orders")
class OrderController(
    private val orderFacade: OrderFacade
) : OrderApiSpecification {

    @PostMapping
    override fun createOrder(@RequestBody request: OrderCreateRequest): ResponseEntity<OrderCreateResponse> {
        val orderResult = orderFacade.createOrder(OrderCreateRequest.from(request))

        val response = OrderCreateResponse.from(orderResult)

        return ResponseEntity.created(URI.create("api/v1/orders/${response.id}")).body(response)
    }
}
