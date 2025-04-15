package kr.hhplus.be.server.domain.order

import java.util.concurrent.ConcurrentHashMap

class FakeOrderRepository : OrderRepository {
    private val orders = ConcurrentHashMap<Long, Order>()

    override fun save(order: Order): Order {
        orders.put(order.id, order)
        return order
    }

    override fun findById(id: Long): Order? {
        return orders.values.find { it.id == id }
    }
}