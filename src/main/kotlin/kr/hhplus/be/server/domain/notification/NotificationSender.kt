package kr.hhplus.be.server.domain.notification

interface NotificationSender {
    fun send(info: NotificationInfo)
}
