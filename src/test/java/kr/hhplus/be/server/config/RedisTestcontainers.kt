package kr.hhplus.be.server.config

import com.redis.testcontainers.RedisContainer
import jakarta.annotation.PreDestroy
import org.springframework.context.annotation.Configuration

@Configuration
class RedisTestcontainers {

    @PreDestroy
    fun preDestroy() {
        if (redisContainer.isRunning) redisContainer.stop()
    }

    companion object {

        private val redisContainer = RedisContainer("redis:7.0.0")
            .apply {
                start()
            }

        init {
            System.setProperty("spring.data.redis.database", "0")
            System.setProperty("spring.data.redis.master.host", redisContainer.host)
            System.setProperty("spring.data.redis.master.port", redisContainer.firstMappedPort.toString())
        }
    }
}
