package kr.hhplus.be.server.infrastructure.cache

import kr.hhplus.be.server.application.cache.CacheTemplate
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisCacheTemplate<K : Any, V : Any>(
    @Qualifier("cacheTemplate")
    private val cache: RedisTemplate<K, V>
) : CacheTemplate<K, V> {

    override fun put(key: K, value: V, expireAfter: Duration) {
        cache.opsForValue().set(key, value, expireAfter)
    }

    override fun get(key: K): V? {
        return cache.opsForValue().get(key)
    }

    override fun remove(key: K) {
        cache.delete(key)
    }
}
