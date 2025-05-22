package kr.hhplus.be.server.infrastructure.notification

import kr.hhplus.be.server.domain.notification.NotificationInfo
import kr.hhplus.be.server.domain.notification.NotificationSender
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class NotificationSenderImpl : NotificationSender {

    private val logger = LoggerFactory.getLogger(NotificationSenderImpl::class.java)

    override fun send(info: NotificationInfo) {
        logger.info("알림 전송")
    }
}
