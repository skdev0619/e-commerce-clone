package kr.hhplus.be.server.interfaces.cash

import kr.hhplus.be.server.application.cash.UserCashChargeResult
import kr.hhplus.be.server.application.cash.UserCashChargeService
import kr.hhplus.be.server.application.cash.UserCashQueryService
import kr.hhplus.be.server.application.cash.UserCashViewResult
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import java.math.BigDecimal

@WebMvcTest(CashController::class)
class CashControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var cashChargeService: UserCashChargeService

    @MockitoBean
    private lateinit var cashQueryService: UserCashQueryService

    @DisplayName("특정 사용자 잔액 조회")
    @Nested
    inner class View {
        @DisplayName("사용자 id로 잔액 조회 시, 사용자 잔액 정보를 반환한다")
        @Test
        fun findByUserId() {
            val userId = 1L
            val userCash = UserCashViewResult(1L, userId, BigDecimal(5_000))
            val response = CashViewResponse.from(userCash)

            `when`(cashQueryService.findByUserId(userId)).thenReturn(userCash)

            mockMvc.get("/api/v1/cash/${userId}") {
                contentType = MediaType.APPLICATION_JSON
            }.andExpect {
                status { isOk() }
                jsonPath("$.id") { value(response.id.toInt()) }
                jsonPath("$.userId") { value(response.userId.toInt()) }
                jsonPath("$.balance") { value(response.balance.toInt()) }
            }

            verify(cashQueryService).findByUserId(userId)
        }

        @DisplayName("존재하지 않는 사용자 id로 잔액 조회 시, not found Error 를 반환한다")
        @Test
        fun findByNonExistId() {
            val userId = 99L

            `when`(cashQueryService.findByUserId(userId))
                .thenThrow(NoSuchElementException("해당 사용자의 잔액이 존재하지 않습니다"))

            mockMvc.get("/api/v1/cash/${userId}") {
                contentType = MediaType.APPLICATION_JSON
            }.andExpect {
                status { isNotFound() }
                jsonPath("$.code") { value(404) }
                jsonPath("$.message") { value("해당 사용자의 잔액이 존재하지 않습니다") }
            }

            verify(cashQueryService).findByUserId(userId)
        }

        @DisplayName("0 이하의 사용자 id로 잔액 조회 시, bad request 를 반환한다")
        @Test
        fun findByNonPositiveId() {
            val userId = 0L

            mockMvc.get("/api/v1/cash/${userId}") {
                contentType = MediaType.APPLICATION_JSON
            }.andExpect {
                status { isBadRequest() }
                jsonPath("$.code") { value(400) }
                jsonPath("$.message") { value("사용자 id는 1 이상이어야 합니다") }
            }

            verify(cashQueryService, times(0)).findByUserId(userId)
        }
    }

    @DisplayName("잔액 충전")
    @Nested
    inner class Charge {

        @DisplayName("사용자의 잔액을 충전하면, 충전 후의 잔액 정보를 반환한다")
        @Test
        fun charge() {
            val userId = 11L
            val amount = BigDecimal(5000)
            val userCash = UserCashChargeResult(1L, userId, amount, BigDecimal(5000))
            val response = CashChargeResponse.from(userCash)

            `when`(cashChargeService.charge(userId, amount)).thenReturn(userCash)

            mockMvc.patch("/api/v1/cash/${userId}/charge") {
                contentType = MediaType.APPLICATION_JSON
                content = amount.toString()
            }.andExpect {
                status { isOk() }
                jsonPath("$.id") { value(response.id.toInt()) }
                jsonPath("$.userId") { value(response.userId.toInt()) }
                jsonPath("$.amount") { value(response.amount.toInt()) }
                jsonPath("$.finalBalance") { value(response.finalBalance.toInt()) }
            }

            verify(cashChargeService).charge(userId, amount)
        }

        @DisplayName("존재하지 않는 사용자 id로 잔액 충전 시, not found Error 를 반환한다")
        @Test
        fun chargeByNonExistId() {
            val userId = 99L
            val amount = BigDecimal(5000)

            `when`(cashChargeService.charge(userId, amount))
                .thenThrow(NoSuchElementException("해당 사용자의 잔액이 존재하지 않습니다"))

            mockMvc.patch("/api/v1/cash/${userId}/charge") {
                contentType = MediaType.APPLICATION_JSON
                content = amount.toString()
            }.andExpect {
                status { isNotFound() }
                jsonPath("$.code") { value(404) }
                jsonPath("$.message") { value("해당 사용자의 잔액이 존재하지 않습니다") }
            }

            verify(cashChargeService).charge(userId, amount)
        }

        @DisplayName("0 이하의 사용자 id로 잔액 충전 시, bad request 를 반환한다")
        @Test
        fun chargeByNonPositiveId() {
            val userId = 0L
            val amount = BigDecimal(5000)

            mockMvc.patch("/api/v1/cash/${userId}/charge") {
                contentType = MediaType.APPLICATION_JSON
                content = amount.toString()
            }.andExpect {
                status { isBadRequest() }
                jsonPath("$.code") { value(400) }
                jsonPath("$.message") { value("사용자 id는 1 이상이어야 합니다") }
            }

            verify(cashChargeService, times(0)).charge(userId, amount)
        }

        @DisplayName("0 이하의 잔액 충전 시, bad request 를 반환한다")
        @Test
        fun chargeByNonPositiveAmount() {
            val userId = 1L
            val amount = BigDecimal(-1000)

            mockMvc.patch("/api/v1/cash/${userId}/charge") {
                contentType = MediaType.APPLICATION_JSON
                content = amount.toString()
            }.andExpect {
                status { isBadRequest() }
                jsonPath("$.code") { value(400) }
                jsonPath("$.message") { value("충전할 잔액은 0보다 커야 합니다") }
            }

            verify(cashChargeService, times(0)).charge(userId, amount)
        }
    }
}
