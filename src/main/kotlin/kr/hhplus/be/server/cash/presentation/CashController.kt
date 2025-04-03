package kr.hhplus.be.server.cash.presentation

import kr.hhplus.be.server.cash.presentation.dto.CashChargeResponse
import kr.hhplus.be.server.cash.presentation.dto.CashDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/vi/cash")
@RestController
class CashController : CashApiSpecification {

    @GetMapping("/{id}")
    override fun point(@PathVariable id: Long): ResponseEntity<CashDto> {
        val response = CashDto(id, 10_000)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("/{id}/charge")
    override fun charge(@PathVariable("id") id: Long, @RequestBody amount: Long): ResponseEntity<CashChargeResponse> {
        val response = CashChargeResponse(id, amount, 10_000 + amount)
        return ResponseEntity.ok(response)
    }
}
