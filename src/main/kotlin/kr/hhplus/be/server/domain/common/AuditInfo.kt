package kr.hhplus.be.server.domain.common

import java.time.LocalDateTime

class AuditInfo(
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val createdBy: String = "master",
    var lastModifiedDate: LocalDateTime = LocalDateTime.now(),
    var lastModifiedBy: String = "master"
) {
    fun update(modifier: String = "master") {
        lastModifiedDate = LocalDateTime.now()
        lastModifiedBy = modifier
    }
}
