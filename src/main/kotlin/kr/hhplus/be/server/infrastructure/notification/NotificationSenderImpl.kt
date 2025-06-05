package kr.hhplus.be.server.infrastructure.notification

import kr.hhplus.be.server.domain.notification.NotificationInfo
import kr.hhplus.be.server.domain.notification.NotificationSender
import org.springframework.stereotype.Component

@Component
class NotificationSenderImpl : NotificationSender {
    override fun send(info: NotificationInfo) {
        TODO("Not yet implemented")
    }
}