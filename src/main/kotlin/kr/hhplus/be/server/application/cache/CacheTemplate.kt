package kr.hhplus.be.server.application.cache

import java.time.Duration

interface CacheTemplate<K, V> {
    fun put(key: K, value: V, expireAfter: Duration)
    fun get(key: K): V?
    fun remove(key: K)
}