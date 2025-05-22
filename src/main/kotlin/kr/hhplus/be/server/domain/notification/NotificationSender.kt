package kr.hhplus.be.server.domain.notification

import org.springframework.stereotype.Component

@Component
interface NotificationSender {
    fun send(info: NotificationInfo)
}
