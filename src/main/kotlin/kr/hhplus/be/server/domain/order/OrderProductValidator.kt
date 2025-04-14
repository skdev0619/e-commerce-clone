package kr.hhplus.be.server.domain.order

import org.springframework.stereotype.Component

@Component
class OrderProductValidator {

    fun validate(products: List<OrderProductSnapShot>, orderItems: OrderItems) {
        orderItems.items.forEach { item ->
            val product = products.find { it.productId == item.productId }
                ?: throw NoSuchElementException("해당 상품이 존재하지 않습니다")

            if (item.quantity > product.stock) {
                throw IllegalStateException("상품의 재고가 부족합니다")
            }
        }
    }
}
