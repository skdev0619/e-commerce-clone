package kr.hhplus.be.server.domain.user

import jakarta.persistence.*
import kr.hhplus.be.server.domain.common.BaseEntity

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false, unique = true)
    val loginId: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val phoneNumber: String
) : BaseEntity() {

}