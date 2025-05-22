package kr.hhplus.be.server.infrastructure.config.async

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@Configuration
@EnableAsync
class AsyncConfig {

    @Bean
    fun taskExecutor(): Executor {
        val taskExecutor = ThreadPoolTaskExecutor()

        taskExecutor.setCorePoolSize(5)
        taskExecutor.setMaxPoolSize(10)
        taskExecutor.setQueueCapacity(100)

        taskExecutor.setThreadNamePrefix("async-task-")
        taskExecutor.setThreadGroupName("async-group")

        return taskExecutor
    }
}
