package kr.hhplus.be.server.infrastructure.lock

import kr.hhplus.be.server.application.lock.DistributedLockManager
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.TimeUnit

@Component
class LettuceDistributedLockManager(
    private val redisTemplate: RedisTemplate<String, String>
) : DistributedLockManager {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    companion object {
        private const val RETRY_INTERVAL_MILLIS = 300L
    }

    override fun <T> executeWithLocks(
        keys: List<String>,
        waitTime: Long,
        leaseTime: Long,
        timeUnit: TimeUnit,
        action: () -> T
    ): T {
        val lockInfos = mutableMapOf<String, String>()
        val leaseTimeMillis = timeUnit.toMillis(leaseTime)
        val waitUntil = System.currentTimeMillis() + timeUnit.toMillis(waitTime)

        while (System.currentTimeMillis() < waitUntil) {
            var allLocked = true

            keys.forEach { key ->
                val value = UUID.randomUUID().toString()
                log.info("lettuce 락 획득 시도 - 락 이름: ${key}")
                val success = redisTemplate.opsForValue()
                    .setIfAbsent(key, value, leaseTimeMillis, TimeUnit.MILLISECONDS)

                if (success == true) {
                    lockInfos[key] = value
                    log.info("lettuce 락 획득 완료 - 락 이름: ${key}")
                } else {
                    allLocked = false
                    log.info("lettuce 락 획득 실패 - 락 이름: ${key}")
                }
            }

            if (allLocked) {
                try {
                    return action()
                } finally {
                    releaseLocks(lockInfos)
                }
            }

            //실패했으면 잠깐 대기 후 다시 시도
            Thread.sleep(RETRY_INTERVAL_MILLIS)
        }

        throw RuntimeException("lettuce 락 획득 실패 - ${keys.joinToString()}")
    }

    override fun <T> executeWithLock(
        lockKey: String,
        waitTime: Long,
        leaseTime: Long,
        timeUnit: TimeUnit,
        action: () -> T
    ): T {
        return executeWithLocks(listOf(lockKey), waitTime, leaseTime, timeUnit, action)
    }

    private fun releaseLocks(locks: Map<String, String>) {
        log.info("lettuce 락 해제 start - 락 이름: ${locks.keys}")
        val ops = redisTemplate.opsForValue()
        locks.forEach { (key, value) ->
            if (ops.get(key) == value) {
                redisTemplate.delete(key)
                log.info("lettuce 락 해제 완료 - 락 이름: ${key}")
            }
        }
    }
}
