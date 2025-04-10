package kr.hhplus.be.server.interfaces.cash

import kr.hhplus.be.server.application.cash.UserCashChargeService
import kr.hhplus.be.server.application.cash.UserCashQueryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RequestMapping("/api/v1/cash")
@RestController
class CashController(
    private val userCashQueryService: UserCashQueryService,
    private val userCashChargeService: UserCashChargeService
) : CashApiSpecification {

    @GetMapping("/{userId}")
    override fun point(@PathVariable("userId") id: Long): ResponseEntity<CashViewResponse> {
        require(id > 0) { "사용자 id는 1 이상이어야 합니다" }
        val userCash = userCashQueryService.findByUserId(id)
        return ResponseEntity.ok(CashViewResponse.from(userCash))
    }

    @PatchMapping("/{userId}/charge")
    override fun charge(
        @PathVariable("userId") id: Long,
        @RequestBody amount: Long
    ): ResponseEntity<CashChargeResponse> {
        require(id > 0) { "사용자 id는 1 이상이어야 합니다" }
        require(amount > 0) { "충전할 잔액은 0보다 커야 합니다" }

        val userCash = userCashChargeService.charge(id, BigDecimal(amount))
        return ResponseEntity.ok(CashChargeResponse.from(userCash))
    }
}
