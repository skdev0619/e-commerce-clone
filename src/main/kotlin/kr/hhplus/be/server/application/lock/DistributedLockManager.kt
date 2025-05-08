package kr.hhplus.be.server.application.lock

import java.util.concurrent.TimeUnit

interface DistributedLockManager {

    fun <T> executeWithLocks(
        lockKeys: List<String>,
        waitTime: Long,
        leaseTime: Long,
        timeUnit: TimeUnit,
        action: () -> T
    ): T

    fun <T> executeWithLock(
        lockKey: String,
        waitTime: Long,
        leaseTime: Long,
        timeUnit: TimeUnit,
        action: () -> T
    ): T
}