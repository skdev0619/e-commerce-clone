package kr.hhplus.be.server.application.order.internal

import kr.hhplus.be.server.domain.order.OrderProductSnapShot
import kr.hhplus.be.server.domain.product.ProductRepository
import org.springframework.stereotype.Component

/*
* 주문 생성 당시의, 주문한 상품의 정보(가격, 재고) 조회
* 주문 도메인의 OrderProductSnapShot VO를 리턴
* */

@Component
class OrderProductSnapShotReader(
    private val productRepository: ProductRepository
) {
    fun read(productIds: List<Long>): List<OrderProductSnapShot> {
        val products = productRepository.findByIdIn(productIds)

        return products.map {
            OrderProductSnapShot(it.id, it.price, it.stock)
        }
    }
}
