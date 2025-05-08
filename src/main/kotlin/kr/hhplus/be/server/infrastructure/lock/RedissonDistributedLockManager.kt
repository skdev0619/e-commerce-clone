package kr.hhplus.be.server.infrastructure.lock

import kr.hhplus.be.server.application.lock.DistributedLockManager
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RedissonDistributedLockManager(
    private val redissonClient: RedissonClient
) : DistributedLockManager {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    override fun <T> executeWithLocks(
        lockKeys: List<String>,
        waitTime: Long,
        leaseTime: Long,
        timeUnit: TimeUnit,
        action: () -> T
    ): T {
        val locks = getLocks(lockKeys, waitTime, leaseTime, timeUnit)

        try {
            return action()
        } finally {
            unlockLocks(locks)
        }
    }

    private fun getLocks(
        lockKeys: List<String>,
        waitTime: Long,
        leaseTime: Long,
        timeUnit: TimeUnit
    ): List<RLock> {
        val locks = lockKeys.map { redissonClient.getLock(it) }

        for (lockName in lockKeys) {
            val lock = redissonClient.getLock(lockName)
            log.info("Redisson 락 획득 시도 - 락 이름: ${lockKeys}")
            lock.tryLock(waitTime, leaseTime, timeUnit)
        }
        log.info("Redisson 락 획득 완료 - 락 이름: ${lockKeys}")
        return locks
    }

    private fun unlockLocks(locks: List<RLock>) {
        locks.forEach {
            if (it.isLocked && it.isHeldByCurrentThread) {
                it.unlock()
                log.info("Redisson 락 해제 - 락 이름: ${it.name}")
            }
        }
    }

    override fun <T> executeWithLock(
        lockKey: String,
        waitTime: Long,
        leaseTime: Long,
        timeUnit: TimeUnit,
        action: () -> T
    ): T {
        return executeWithLocks(listOf(lockKey), waitTime, leaseTime, timeUnit) { action() }
    }
}
