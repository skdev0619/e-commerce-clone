package kr.hhplus.be.server.domain

import kr.hhplus.be.server.domain.common.AuditInfo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.lang.Thread.sleep

class AuditInfoTest {

    @DisplayName("수정일이 현재 시간으로 갱신된다")
    @Test
    fun update() {
        val auditInfo = AuditInfo()
        val lastModifiedDate = auditInfo.lastModifiedDate

        sleep(1)
        auditInfo.update()

        assertThat(auditInfo.lastModifiedDate).isAfter(lastModifiedDate)
    }
}
