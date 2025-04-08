package kr.hhplus.be.server.payment.presentation

import kr.hhplus.be.server.payment.presentation.dto.PaymentCreateResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/payments")
class PaymentController : PaymentApiSpecification {

    @PostMapping
    override fun createPayment(@RequestBody orderId: Long): ResponseEntity<PaymentCreateResponse> {
        val response = PaymentCreateResponse(
            1L,
            5L,
            45_000,
            LocalDateTime.of(2025, 4, 3, 12, 0, 0)
        )
        return ResponseEntity.created(URI.create("/api/v1/payments/${response.id}")).body(response)
    }
}