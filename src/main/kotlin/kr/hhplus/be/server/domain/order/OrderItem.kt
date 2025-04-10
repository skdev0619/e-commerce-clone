package kr.hhplus.be.server.domain.order

import kr.hhplus.be.server.domain.common.AuditInfo
import java.math.BigDecimal

class OrderItem(
    val id: Long,
    val productId: Long,
    val quantity: Int,
    val price: BigDecimal,
    val auditInfo: AuditInfo = AuditInfo()
) {
    constructor(productId: Long, quantity: Int, price: BigDecimal) : this(0L, productId, quantity, price)

    fun amount(): BigDecimal {
        return price.multiply(BigDecimal(quantity))
    }
}
