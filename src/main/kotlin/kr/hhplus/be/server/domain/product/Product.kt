package kr.hhplus.be.server.domain.product

import kr.hhplus.be.server.domain.common.AuditInfo
import java.math.BigDecimal

class Product(
    val id: Long = 0L,
    val name: String,
    val price: BigDecimal,
    var stock: Int,
    val auditInfo: AuditInfo = AuditInfo()
) {
    constructor(name: String, price: Int, stock: Int) : this(0L, name, BigDecimal(price), stock)

    fun decreaseStock(quantity: Int) {
        require(stock >= quantity) { "상품의 재고가 부족합니다." }
        stock -= quantity
        auditInfo.update()
    }
}
