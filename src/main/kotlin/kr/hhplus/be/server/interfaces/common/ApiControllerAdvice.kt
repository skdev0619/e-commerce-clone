package kr.hhplus.be.server.interfaces.common

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

data class ErrorResponse(val code: Int, val message: String)

//@RestControllerAdvice
class ApiControllerAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(400, e.message ?: "잘못된 요청입니다."),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementException(e: NoSuchElementException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(404, e.message ?: "요청한 리소스가 존재하지 않습니다."),
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(500, e.message ?: "에러가 발생했습니다."),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}
