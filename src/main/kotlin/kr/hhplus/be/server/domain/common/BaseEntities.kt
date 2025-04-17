package kr.hhplus.be.server.domain.common

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.domain.AbstractAggregateRoot
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity(

    @CreatedDate
    @Column(updatable = false)
    var createdDate: LocalDateTime = LocalDateTime.now(),

    @CreatedBy
    @Column(updatable = false)
    var createdBy: String = "master",

    @LastModifiedDate
    var lastModifiedDate: LocalDateTime = LocalDateTime.now(),

    @LastModifiedBy
    var lastModifiedBy: String = "master"
) {
}

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseRootEntity<T : AbstractAggregateRoot<T>>(
    @CreatedDate
    @Column(updatable = false)
    var createdDate: LocalDateTime = LocalDateTime.now(),

    @CreatedBy
    @Column(updatable = false)
    var createdBy: String = "master",

    @LastModifiedDate
    var lastModifiedDate: LocalDateTime = LocalDateTime.now(),

    @LastModifiedBy
    var lastModifiedBy: String = "master"
) : AbstractAggregateRoot<T>() {
}
