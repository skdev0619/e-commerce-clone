package kr.hhplus.be.server.domain.notification

data class NotificationInfo(
    val target: String,
    val message: String
)

//data class EmailNotification(
//    val email: String,
//    override val message: String
//) : NotificationInfo()
//
//data class SmsNotification(
//    val phoneNumber: String,
//    override val message: String
//) : NotificationInfo()
