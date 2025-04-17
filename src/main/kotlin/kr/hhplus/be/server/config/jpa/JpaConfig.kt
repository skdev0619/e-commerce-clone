package kr.hhplus.be.server.config.jpa

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import java.util.*

@Configuration
@EnableJpaAuditing
class JpaConfig {
    @Bean
    fun transactionManager(): PlatformTransactionManager {
        return JpaTransactionManager()
    }

    //TODO 로그인 구현 시 변경할 것
    @Bean
    fun auditorProvider(): AuditorAware<String> {
        return AuditorAware { Optional.of("master") }
    }
}
