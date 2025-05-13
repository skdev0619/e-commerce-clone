package kr.hhplus.be.server.application.order

import kr.hhplus.be.server.application.lock.DistributedLockManager
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class OrderLockTemplate(
    @Qualifier("redissonDistributedLockManager")
    private val lockManager: DistributedLockManager,
    private val orderFacade: OrderFacade
) {
    companion object {
        private const val DEFAULT_WAIT_TIME_MILLIS = 70000L   // 락을 시도할 최대 대기 시간
        private const val DEFAULT_LEASE_TIME_MILLIS = 60000L  // 락 점유 시간 (TTL)
    }

    fun createOrder(criteria: OrderCriteria): OrderCompletedResult {
        val lockKeys = criteria.toProductQuantities()
            .map { "product-stoc-:${it.productId}" }

        return lockManager.executeWithLocks(
            lockKeys,
            DEFAULT_WAIT_TIME_MILLIS,
            DEFAULT_LEASE_TIME_MILLIS,
            TimeUnit.MILLISECONDS
        ) { orderFacade.createOrder(criteria) }
    }
}
