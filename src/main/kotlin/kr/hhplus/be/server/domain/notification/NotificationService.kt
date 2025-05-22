package kr.hhplus.be.server.domain.notification

import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val sender: NotificationSender
) {
    fun send(info: NotificationInfo) {
        sender.send(info)
    }
}
